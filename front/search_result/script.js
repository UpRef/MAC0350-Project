// Fetch JSON data from a file and use it
fetch('./search_result/search_results.json')
  .then(response => response.json())
  .then(json => {
    const el = document.querySelector(".root-1");
    var tt1 = new table(el);
    tt1.addData(json);
    tt1.interactivity(); // Ensure any interactivity is initialized after data is loaded
  })
  .catch(error => console.error('Error loading JSON data:', error));