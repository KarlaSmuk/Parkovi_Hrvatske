let auth0Client = null;
let loggingOut = false;

const fetchAuthConfig = () => fetch("/auth_config.json");

const configureClient = async () => {
  const response = await fetchAuthConfig();
  const config = await response.json();

  // Check if auth0 is defined before using it
  if (typeof auth0 === "undefined") {
    console.error("Auth0 SDK is not loaded.");
    return;
  }
  auth0Client = await auth0.createAuth0Client({
    domain: config.domain,
    clientId: config.client_id,
    scope: 'openid profile email'
  });
};

async function initAuth() {
  await configureClient();

  updateUI();

  // handle coming back from login
  const query = window.location.search;
  if (query.includes("code=") && query.includes("state=")) {
    // const accessToken = await auth0Client.getTokenSilently();
    // localStorage.setItem('accessToken', accessToken);

    try {
      // Process the login state
      console.log("Handling redirect callback...");
      await auth0Client.handleRedirectCallback();
      console.log("Redirect callback handled.");

      if (isTokenExpired(localStorage.getItem("id_token"))) {
        const claims = await auth0Client.getIdTokenClaims();
        const id_token = claims.__raw;
        const decodedToken = JSON.parse(atob(id_token.split(".")[1]));

        // // Calculate the expiration time
        const expiresAt = new Date(decodedToken.exp * 1000);

        console.log(expiresAt);
        console.log(decodedToken)

        localStorage.setItem("id_token", id_token);
      }

      console.log(isTokenExpired(localStorage.getItem("id_token")));

      updateUI();

      // Use replaceState to redirect the user away and remove the querystring parameters
      window.history.replaceState({}, document.title, "/");
    } catch (error) {
      // Handle errors, e.g., if the user needs to log in again
      console.error("Error handling redirect callback:", error);
      // Redirect the user to the login page with a full-page redirect
      await auth0Client.loginWithRedirect();
    }
  }
}

const updateUI = async () => {
  const isAuthenticated = await auth0Client.isAuthenticated();

  if (isAuthenticated) {
    document.getElementById("btn-logout").classList.remove("hidden");
    document.getElementById("btn-login").classList.add("hidden");
  } else {
    document.getElementById("btn-login").classList.remove("hidden");
    document.getElementById("btn-logout").classList.add("hidden");
  }

  // document.getElementById("btn-logout").disabled = !isAuthenticated;
  // document.getElementById("btn-login").disabled = isAuthenticated;

  const downloadButtons = document.querySelectorAll(".download-button");
  downloadButtons.forEach((button) => {
    button.disabled = !isAuthenticated;
  });

  const profileLink = document.getElementById("profile-link");
  const refreshLink = document.getElementById("refresh-data-link");

  // Check if the user is on the korisnicki-profil page
  const isUserProfilePage = window.location.pathname === "/korisnicki-profil";
  const isRefreshPage = window.location.pathname === "/osvjezi-preslike";

  if (isAuthenticated) {
    // Show the profile link
    profileLink.classList.remove("hidden");
    refreshLink.classList.remove("hidden");

    refreshLink.addEventListener("click", async function (event) {
      try {
        // Fetch data from the backend for refreshing
        const response = await fetch("http://localhost:8080/refreshData", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }

        // Handle the response if needed
        console.log("Refreshed data successfully");
      } catch (error) {
        // Handle errors
        console.error("Error refreshing preslike:", error);
      }
    });

    // Check if the user is on the korisnicki-profil page
    const isUserProfilePage = window.location.pathname === "/korisnicki-profil";

    if (isUserProfilePage) {
      // Update the look of the page for authenticated users on the user profile page
      document.body.innerHTML = `
              <h1>Korisnički profil</h1>
              <div id="user-info"></div>
            `;

      try {
        const userInfo = await auth0Client.getUser();

        console.log(userInfo)

        const userInfoDiv = document.getElementById("user-info");
        userInfoDiv.innerHTML = `
                  <img src="${userInfo.picture}" alt="Profile Picture">
                  <p>Email: ${userInfo.email}</p>
                  <p>Nickname: ${userInfo.nickname}</p>
              `;
      } catch (error) {
        console.error("Error fetching user information:", error);
      }
    }

    if (isRefreshPage) {
      document.body.innerHTML = `
          <h1>Preuzmi podatke iz baze.</h1>
              <div class="download-buttons-container">
                  <button class="download-button" onclick="downloadAsJSON()">
                      Preuzmi skup podataka iz baze u JSON formatu
                  </button>
                  <button class="download-button" onclick="downloadAsCSV()">
                    Preuzmi skup podataka iz baze u CSV formatu
                  </button>
              </div>
          `;
    }
  } else {
    refreshLink.classList.add("hidden");
    profileLink.classList.add("hidden");

    if (isUserProfilePage || isRefreshPage) {
      // User is not authenticated, throw an error
      throw new Error("User is not authenticated");
    }
  }
};

const login = async () => {
  const token = localStorage.getItem("id_token");
  const expired = isTokenExpired(token);

  if (expired) {
    await auth0Client.loginWithRedirect({
      authorizationParams: {
        redirect_uri: window.location.origin,
      },
    });
  } else {
    await auth0Client.loginWithRedirect({
      authorizationParams: {
        redirect_uri: window.location.origin,
        prompt: "none",
      },
    });
  }
};

const logout = async () => {
  await auth0Client.logout({
    onRedirect: async () => {
      console.log("Logging out localy...");
    },
  });

  updateUI();
};

const isTokenExpired = (token) => {
  if (!token) {
    // Token not provided
    return true;
  }

  const decodedToken = JSON.parse(atob(token.split(".")[1]));
  const expirationTime = decodedToken.exp * 1000; // Convert to milliseconds

  return Date.now() > expirationTime;
};
