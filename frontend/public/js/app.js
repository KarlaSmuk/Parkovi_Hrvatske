


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

    fetch("http://localhost:8080/park")
        .then(res => {
            return res.json();
        })
        .then((data) => {
            // new DataTable('#example', {
            //     ajax: data,
            //     columns: [
            //         { data: 'parkName' },
            //         { data: 'typeOfPark' },
            //         { data: 'yearOfFoundation' },
            //         { data: 'peakName' },
            //         { data: 'peakHeight' },
            //         { data: 'counties' },
            //         { data: 'atraction' },
            //         { data: 'event' },
            //         { data: 'animals' }
            //     ],
            //     processing: true
            // });
            fillTableWithData(data);
        });

}

function fillTableWithData(data) {
    // const trTemplate = document.querySelector("[data-template]");
    // const tableHeadRows = document.querySelectorAll(".th-row > th > h4");
    // const tableBody = document.querySelector(".table > tbody");
    // const trows = tableBody.getElementsByTagName("tr");

    // data.forEach((park) => {

    //     const trElement = trTemplate.content.cloneNode(true).children[0];


    //     const length = park.counties.length > park.animals.length ? park.counties.length : park.animals.length;


    //     park.forEach((p) => {

    //         const oznzgradaElement = trElement.querySelector(".parkName");
    //         const ozndvoranaElement = trElement.querySelector(".typeOfPark");
    //         const kapacitetElement = trElement.querySelector(".yearOfFoundation");
    //         const brojstolovaElement = trElement.querySelector(".area");
    //         const brojstolicaElement = trElement.querySelector(".peakName");
    //         const imaklimuElement = trElement.querySelector(".peakHeight");
    //         const imaprirodnusvjetlostElement = trElement.querySelector(".county");
    //         const imauticniceElement = trElement.querySelector(".atraction");
    //         const dvoranasopremomElement = trElement.querySelector(".event");
    //         const imaracunaloElement = trElement.querySelector(".animal");
    //         const imahdmikabelElement = trElement.querySelector(".animalSpecies");

    //         oznzgradaElement.firstChild.innerText = park.parkName;
    //         ozndvoranaElement.firstChild.innerText = park.typeOfPark;
    //         kapacitetElement.firstChild.innerText = park.yearOfFoundation;
    //         brojstolovaElement.firstChild.innerText = park.area;
    //         brojstolicaElement.firstChild.innerText = park.peakName;
    //         imaklimuElement.firstChild.innerText = park.peakHeight;
    //         imaprirodnusvjetlostElement.firstChild.innerText = park.county;
    //         imauticniceElement.firstChild.innerText = park.atraction;
    //         dvoranasopremomElement.firstChild.innerText = park.event;
    //         imaracunaloElement.firstChild.innerText = park.animal;
    //         imahdmikabelElement.firstChild.innerText = park.animalSpecies;

    //         tableBody.append(trElement);

    //     })


    // })


    const tableBody = document.querySelector(".table > tbody");

    data.forEach((park) => {
        park.animals.forEach((animal) => {
            
            if (park.counties.length > 1) {
               
                park.counties.forEach((county) => {
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
                    countiesCell.innerText = county; 

                    const atractionCell = document.createElement("td");
                    atractionCell.innerText = park.atraction;

                    const eventCell = document.createElement("td");
                    eventCell.innerText = park.event;

                    const animalNameCell = document.createElement("td");
                    animalNameCell.innerText = animal.name;

                    const animalSpeciesCell = document.createElement("td");
                    animalSpeciesCell.innerText = animal.species;

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
               
            } else {
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
                countiesCell.innerText = park.counties; // Join counties as a comma-separated string

                const atractionCell = document.createElement("td");
                atractionCell.innerText = park.atraction;

                const eventCell = document.createElement("td");
                eventCell.innerText = park.event;

                const animalNameCell = document.createElement("td");
                animalNameCell.innerText = animal.name;

                const animalSpeciesCell = document.createElement("td");
                animalSpeciesCell.innerText = animal.species;

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
            }




        });
    });


}


