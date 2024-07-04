searchform = document.querySelector("#search-form")

EventListener('DOMContentLoaded', function() {
    const searchForm = document.querySelector("#search-form");
  
    searchForm.addEventListener("submit", function(event) {
        event.preventDefault();
        alert("alert");
        search();
    });
})

search = function () {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append('Accept', 'application/json');
    
    const category = document.querySelector("#selected-category").textContent
    const query = document.querySelector("#search-input").value
    console.log(category, query)
    url = 'http://127.0.0.1:8080/search/' + category + '/' + query
    
    const request = {
      method: 'GET',
      headers: myHeaders,
      redirect: "follow"
    }

    fetch(url, request)
    .then(response => response.json()) // Parse JSON
    .then(data => {
        loadFolderPage(query, category)
        writeResultsPage(data)
    })
};


function loadFolderPage(query, category) {
  fetch('./search_result/demo.html')
      .then(response => response.text())
      .then(data => {
          document.getElementById('content').innerHTML = data;
      })
      .catch(error => console.error('Deu erro :(', error));   
}

function writeResultsPage(paperList) {
  let results_div = document.querySelector("#results");
  let title = document.querySelector("#folder-name");
  title.innerText = 'Resultados da busca por ('+category+'): ' + query

  var results = ""

  paperList.forEach((paper) => {
      results += '<div class="resultado" style="background-color: #ccc;">'
          +'<div class="title"><b>title</b>: '+paper.title+'</div>'
          +'<div class="authors"><b>authors</b>: '+paper.authors+'</div>'
          +'<div class="abstract"><b>abstract</b>: '+paper.abstract+'</div>'
          +'<div><span>remove</span></div>'
          +'</div><div style="background-color: "#fff"><br></div>';
  })

  results_div.innerHTML = results
}
