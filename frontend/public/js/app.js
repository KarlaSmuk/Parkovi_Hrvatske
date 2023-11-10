function fetchAll() {
  fetch("http://localhost:8080/parks")
    .then((res) => {
      return res.json();
    })
    .then((data) => {
      fillTable(data);
    });
}

function fillTable(data) {
  const tableBody = document.querySelector(".table > tbody");

  data.forEach((park) => {
    const trElement = document.createElement("tr");

    const parkNameCell = document.createElement("td");
    parkNameCell.innerText = park.parkName;

    const typeOfParkCell = document.createElement("td");
    typeOfParkCell.innerText = park.typeOfPark;

    const yearOfFoundationCell = document.createElement("td");
    yearOfFoundationCell.innerText = park.yearOfFoundation;

    const areaCell = document.createElement("td");
    areaCell.innerText = park.area;

    const peakNameCell = document.createElement("td");
    peakNameCell.innerText = park.peakName;

    const peakHeightCell = document.createElement("td");
    peakHeightCell.innerText = park.peakHeight;

    const countiesCell = document.createElement("td");
    countiesCell.innerText = park.county;

    const atractionCell = document.createElement("td");
    atractionCell.innerText = park.atraction;

    const eventCell = document.createElement("td");
    eventCell.innerText = park.event;

    const animalNameCell = document.createElement("td");
    animalNameCell.innerText = park.animal;

    const animalSpeciesCell = document.createElement("td");
    animalSpeciesCell.innerText = park.species;

    trElement.appendChild(parkNameCell);
    trElement.appendChild(typeOfParkCell);
    trElement.appendChild(yearOfFoundationCell);
    trElement.appendChild(areaCell);
    trElement.appendChild(peakNameCell);
    trElement.appendChild(peakHeightCell);
    trElement.appendChild(countiesCell);
    trElement.appendChild(atractionCell);
    trElement.appendChild(eventCell);
    trElement.appendChild(animalNameCell);
    trElement.appendChild(animalSpeciesCell);

    tableBody.appendChild(trElement);
  });
}

function filterTable() {
  const columnSelect = document.querySelector(".column-selector");
  const searchInputElement = document.querySelector(".search-input");

  const searchBy = columnSelect.value;
  const search = searchInputElement.value;

  const url = "http://localhost:8080/search/" + searchBy + "/" + search;

  fetch(url)
    .then((res) => {
      return res.json();
    })
    .then((data) => {
      clearTable();
      sendData(data);
      fillTable(data);
    });
}

function clearTable() {
  const tableBody = document.querySelector(".table > tbody");

  while (tableBody.firstChild) {
    tableBody.removeChild(tableBody.firstChild);
  }
}

function sendData(data) {

    //console.log(JSON.stringify(data));

    fetch('http://localhost:8080/search/sendData', {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      });
    //   .then(response => response.json()) 
    //   .then(data => {
    //       console.log(data);
    //   })
    //   .catch(error => {
    //       console.error('Error:', error);
    //   });

    
}

function downloadAsJSON() {
    
    fetch('http://localhost:8080/search/downloadJson')
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(new Blob([blob]));
            const a = document.createElement('a');
            a.href = url;
            a.download = 'parks.json';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        })
        .catch(error => console.error('Error:', error));

}

function downloadAsCSV() {

    fetch('http://localhost:8080/search/downloadCSV')
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(new Blob([blob]));
            const a = document.createElement('a');
            a.href = url;
            a.download = 'parks.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        })
        .catch(error => console.error('Error:', error));
}
