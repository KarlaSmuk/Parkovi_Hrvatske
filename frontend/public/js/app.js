


function fetchByName() {
    fetch("http://localhost:8080/park/Mljet")
        .then(res => {
            return res.json();
        })
        .then((data) => {
            console.log(data);
        });
}

function fetchAll() {

    fetch("http://localhost:8080/parks")
        .then(res => {
            return res.json();
        })
        .then((data) => {

            fillTable(data);

            //fillDatatableNet(data);
        });

}

// function fillTableWithData(data) {


//     const tableBody = document.querySelector(".table > tbody");

//     data.forEach((park) => {
//         park.animals.forEach((animal) => {

//             if (park.counties.length > 1) {

//                 park.counties.forEach((county) => {
//                     const trElement = document.createElement("tr");

//                     const parkNameCell = document.createElement("td");
//                     parkNameCell.innerText = park.parkName;

//                     const typeOfParkCell = document.createElement("td");
//                     typeOfParkCell.innerText = park.typeOfPark;

//                     const yearOfFoundationCell = document.createElement("td");
//                     yearOfFoundationCell.innerText = park.yearOfFoundation;

//                     const areaCell = document.createElement("td");
//                     areaCell.innerText = park.area;

//                     const peakNameCell = document.createElement("td");
//                     peakNameCell.innerText = park.peakName;

//                     const peakHeightCell = document.createElement("td");
//                     peakHeightCell.innerText = park.peakHeight;

//                     const countiesCell = document.createElement("td");
//                     countiesCell.innerText = county;

//                     const atractionCell = document.createElement("td");
//                     atractionCell.innerText = park.atraction;

//                     const eventCell = document.createElement("td");
//                     eventCell.innerText = park.event;

//                     const animalNameCell = document.createElement("td");
//                     animalNameCell.innerText = animal.name;

//                     const animalSpeciesCell = document.createElement("td");
//                     animalSpeciesCell.innerText = animal.species;

//                     trElement.appendChild(parkNameCell);
//                     trElement.appendChild(typeOfParkCell);
//                     trElement.appendChild(yearOfFoundationCell);
//                     trElement.appendChild(areaCell);
//                     trElement.appendChild(peakNameCell);
//                     trElement.appendChild(peakHeightCell);
//                     trElement.appendChild(countiesCell);
//                     trElement.appendChild(atractionCell);
//                     trElement.appendChild(eventCell);
//                     trElement.appendChild(animalNameCell);
//                     trElement.appendChild(animalSpeciesCell);

//                     tableBody.appendChild(trElement);
//                 })

//             } else {
//                 const trElement = document.createElement("tr");

//                 const parkNameCell = document.createElement("td");
//                 parkNameCell.innerText = park.parkName;

//                 const typeOfParkCell = document.createElement("td");
//                 typeOfParkCell.innerText = park.typeOfPark;

//                 const yearOfFoundationCell = document.createElement("td");
//                 yearOfFoundationCell.innerText = park.yearOfFoundation;

//                 const areaCell = document.createElement("td");
//                 areaCell.innerText = park.area;

//                 const peakNameCell = document.createElement("td");
//                 peakNameCell.innerText = park.peakName;

//                 const peakHeightCell = document.createElement("td");
//                 peakHeightCell.innerText = park.peakHeight;

//                 const countiesCell = document.createElement("td");
//                 countiesCell.innerText = park.counties; // Join counties as a comma-separated string

//                 const atractionCell = document.createElement("td");
//                 atractionCell.innerText = park.atraction;

//                 const eventCell = document.createElement("td");
//                 eventCell.innerText = park.event;

//                 const animalNameCell = document.createElement("td");
//                 animalNameCell.innerText = animal.name;

//                 const animalSpeciesCell = document.createElement("td");
//                 animalSpeciesCell.innerText = animal.species;

//                 trElement.appendChild(parkNameCell);
//                 trElement.appendChild(typeOfParkCell);
//                 trElement.appendChild(yearOfFoundationCell);
//                 trElement.appendChild(areaCell);
//                 trElement.appendChild(peakNameCell);
//                 trElement.appendChild(peakHeightCell);
//                 trElement.appendChild(countiesCell);
//                 trElement.appendChild(atractionCell);
//                 trElement.appendChild(eventCell);
//                 trElement.appendChild(animalNameCell);
//                 trElement.appendChild(animalSpeciesCell);

//                 tableBody.appendChild(trElement);
//             }




//         });
//     });


// }

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
    })


}

function filterTable() {

    const columnSelect = document.querySelector(".column-selector");
    const searchInputElement = document.querySelector(".search-input");

    const searchBy = columnSelect.value;
    const search = searchInputElement.value;

    const url = "http://localhost:8080/search/" + searchBy + "/" + search;


    console.log(url);

    fetch(url)
        .then(res => {
            return res.json();
        })
        .then((data) => {
            clearTable();
            fillTable(data);

        });
}

function clearTable() {
    const tableBody = document.querySelector(".table > tbody");

    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }
}

// function fillDatatableNet() {
//     new DataTable('#example', {
//         "processing": true,
//         "serverSide": true,
//         "ajax": {
//             "url": "http://localhost:8080/park",
//             "dataSrc": ""
//         },

//         columns: [
//             { data: 'parkName' },
//             { data: 'typeOfPark' },
//             { data: 'yearOfFoundation' },
//             { data: 'area' },
//             { data: 'peakName' },
//             { data: 'peakHeight' },
//             { data: 'county' },
//             { data: 'atraction' },
//             { data: 'event' },
//             { data: 'animal' },
//             { data: 'species' }
//         ]
//     });
// }