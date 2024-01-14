const BASE_URL = window.location.origin;
const CHECK_AUTH_URL = "/checkAuthentication";
const LOGIN_URL = "/login";
const LOGOUT_URL = "/logout";
const USER_INFO_URL = "/userInfo";
const PROFILE_URL = "/profile";
const DATA_URL = "/refreshData";

let isAuthenticated = false;

const fetchAuth = async () => {
  try {
    const response = await fetch(CHECK_AUTH_URL);
    return await response.json();
  } catch (error) {
    console.error("Error fetching authentication status:", error);
    throw error;
  }
};

const configureClient = async () => {
  isAuthenticated = await fetchAuth();
};

const updateUI = async () => {
  if (isAuthenticated) {
    document.getElementById("btn-logout").classList.remove("hidden");
    document.getElementById("btn-login").classList.add("hidden");

    document.getElementById("profile-link").classList.remove("hidden");
    document.getElementById("refresh-data-link").classList.remove("hidden");

    document.querySelector(".download-button:nth-child(1)").classList.remove("hidden");
    document.querySelector(".download-button:nth-child(2)").classList.remove("hidden"); 
  } else {
    document.getElementById("btn-login").classList.remove("hidden");
    document.getElementById("btn-logout").classList.add("hidden");

    document.getElementById("profile-link").classList.add("hidden");
    document.getElementById("refresh-data-link").classList.add("hidden");

    document.querySelector(".download-button:nth-child(1)").classList.add("hidden"); 
    document.querySelector(".download-button:nth-child(2)").classList.add("hidden"); 
  }

  const profileLink = document.getElementById("profile-link");
  const refreshLink = document.getElementById("refresh-data-link");

  if (isAuthenticated) {

    refreshLink.addEventListener("click", async function (event) {

      event.preventDefault();

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

    profileLink.addEventListener("click", async function (event) {
      event.preventDefault(); // Prevent the default link behavior (page navigation)

      const userProfileContainer = document.getElementById("user-profile-container");

      if (userProfileContainer.classList.contains("hidden")) {
        try {
          const response = await fetch(USER_INFO_URL);
  
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
  
          const userInfo = await response.json();
  
          // Create and append user profile HTML
          userProfileContainer.innerHTML = `
            <div id="user-info">
              <img src="${userInfo.picture}" alt="Profile Picture">
              <p>sub: ${userInfo.sub}</p>
              <p>Email: ${userInfo.email}</p>
              <p>Name: ${userInfo.name}</p>
              <p>Nickname: ${userInfo.nickname}</p>
              <p>Updated at: ${userInfo.updated_at}</p>
              <p>Email verified: ${userInfo.email_verified}</p>
            </div>
          `;
  
          // Show the user profile container
          userProfileContainer.classList.remove("hidden");
          // Handle the response if needed
          console.log("User data fetch successfully");
        } catch (error) {
          console.error("Error fetching user information:", error);
        }
      } else {
        // Hide the user profile container if it's already visible
        userProfileContainer.classList.add("hidden");
      }
    });
  } else {
    refreshLink.classList.add("hidden");
    profileLink.classList.add("hidden");
  }
};

const login = async () => {
  window.location.href = BASE_URL + LOGIN_URL;
};

const logout = async () => {
  window.location.href = BASE_URL + LOGOUT_URL;
};

async function initAuth() {
  try {
    await configureClient();
    updateUI();
  } catch (error) {
    console.error("Authentication initialization error:", error);
  }
}
