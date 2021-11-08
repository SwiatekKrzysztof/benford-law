function addRealDataToChart(json) {
    mainChart.data.datasets.forEach((dataset) => {
        if (dataset.label === 'Input') {
            if (json.matchesBenfordLaw) {
                dataset.borderColor = 'rgba(0,255,229,0.24)'
                dataset.backgroundColor = 'rgba(0,255,255,0.42)'
            } else {
                dataset.borderColor = 'rgba(255,0,38,0.24)'
                dataset.backgroundColor = 'rgba(255,0,47,0.42)'
            }
            addDataToDataset(dataset, json)
        }
    });
    mainChart.update();
}

function addIdealBenfordDataToChart(json) {
    mainChart.data.datasets.forEach((dataset) => {
        if (dataset.label === 'Benford') {
            addDataToDataset(dataset, json)
        }
    });
    mainChart.update();
}

function addDataToDataset(dataset, json) {
    dataset.data.push(json.onesCount);
    dataset.data.push(json.twosCount);
    dataset.data.push(json.threesCount);
    dataset.data.push(json.foursCount);
    dataset.data.push(json.fivesCount);
    dataset.data.push(json.sixesCount);
    dataset.data.push(json.sevensCount);
    dataset.data.push(json.eightsCount);
    dataset.data.push(json.ninesCount);
}

function removeData() {
    mainChart.data.datasets.forEach((dataset) => {
        dataset.data = []
    });
    mainChart.update();
}

function setupCanvas() {
    const ctx = document.getElementById('mainChart').getContext('2d');
    const labels = ["1", "2", "3", "4", "5", "6", "7", "8", "9"]
    const data = {
        labels: labels,
        datasets: [
            {
                label: 'Input',
                data: [],
                borderColor: 'rgba(0,255,229,0.24)',
                backgroundColor: 'rgba(0,255,255,0.42)'
            }
            ,
            {
                label: 'Benford',
                data: [],
                borderColor: 'rgba(225,190,106,0.42)',
                backgroundColor: 'rgba(225,190,106,0.71)'
            }
        ]
    };
    const config = {
        type: 'bar',
        data: data,
        options: {
            indexAxis: 'y',
            elements: {
                bar: {
                    borderWidth: 2,
                }
            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'right',
                },
                title: {
                    display: true,
                }
            }
        },
    };
    return new Chart(ctx, config);
}
