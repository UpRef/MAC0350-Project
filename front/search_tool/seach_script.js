// Fetch JSON data from a file and use it
/*
fetch('./search_result/search_results.json')
  .then(response => response.json())
  .then(json => {
    const el = document.querySelector(".root-1");
    var tt1 = new table(el);
    tt1.addData(json);
    tt1.interactivity(); // Ensure any interactivity is initialized after data is loaded
  })
  .catch(error => console.error('Error loading JSON data:', error));
*/

searchform = document.querySelectorById("search-form")

searchform.setAttribute("onsubmit", "search()");
console.log('AAAAAAAAAAAAA')
search = function () {
  fetch('http://127.0.0.1:8080/folders/from/user/42', {
      method: 'GET',
      mode: 'no-cors',
      'Access-Control-Allow-Origin': '*'
  }) // Fetch JSON file
  .then(response => response.json()) // Parse JSON
  .then(data => {
    console.log("get")
      const foldersList = document.getElementById('folders-list');
      data.folders.forEach(folder => {
          const li = document.createElement('li');
          const a = document.createElement('a');
          a.setAttribute('onclick', 'loadFolderPage()');
          a.setAttribute('href', `#${folder.name}`);
          a.textContent = folder.name;
          li.appendChild(a);
          foldersList.appendChild(li);
      });

      const li = document.createElement('li');
      const a = document.createElement('a');
      a.setAttribute('onclick', 'addFolder()');
      a.setAttribute('href', `#`);
      a.textContent = "+ adicionar paper"
      li.appendChild(a);
      foldersList.appendChild(li);
  })
};