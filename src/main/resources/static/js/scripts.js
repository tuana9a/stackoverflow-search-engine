var currentPage = 0;

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(JSON.parse(xmlHttp.responseText), 10);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}

document.getElementById("search-form").addEventListener("submit", (e) => {
    e.preventDefault();
    searchRequest();
})

async function searchRequest() {
    query = document.getElementById("query").value;
    url = "http://localhost/api/search?q=" + query + "&limit=100";
    console.log(url);
    httpGetAsync(url, showResult);
    // showResult(data, 4);
}

function showResult(data, articlePerPage = 5) {
    console.log(data);
    document.getElementById("home-page").style.display = "none";
    searchResults = document.getElementById("search-results");
    searchResults.innerHTML = '';
    pagination = document.getElementById("pagination");
    if(data.length > 0 && articlePerPage != 0) {
        pagination.innerHTML = `<li class="page-item"><a class="page-link" id="page-1" href="#">1</a></li>`;
        if (data.length > articlePerPage) {
            pagination.innerHTML = `<li class="page-item"><a class="page-link" id="page-prev" href="#">Previous</a></li>` + pagination.innerHTML;

            var numPages = parseInt(data.length / articlePerPage);
            if (data.length % articlePerPage > 0) numPages++;
            for (let i = 1; i < numPages; i++) {
                pagination.innerHTML += `<li class="page-item"><a class="page-link" id="page-${i+1}" href="#">${i+1}</a></li>`
            }

            pagination.innerHTML += `<li class="page-item"><a class="page-link" id="page-next" href="#">Next</a></li>`
        }
    }

    for (let i = 0; i < data.length; i++){
        if (i % articlePerPage == 0) searchResults.innerHTML += `<div class="result-page" id="result-page-${(i / articlePerPage) + 1}"></div>`
        document.getElementsByClassName('result-page')[document.getElementsByClassName('result-page').length -1].innerHTML += `
            <div class="row py-3">
                <a class="text-decoration-none text-dark" href="${data[i].link}" target="_blank">
                    <p>${data[i].link}</p>
                    <h2>${data[i].title}</h2>
                </a>
                <p>${data[i].content.substring(0,300)}...</p>
            </div>`
    }

    goToPage(0);

    paginationNavs = document.getElementsByClassName('page-link');
    if (paginationNavs.length > 1) {
        paginationNavs[0].addEventListener("click", () => {
            if (currentPage > 0) goToPage(currentPage-1);
        });
        paginationNavs[paginationNavs.length - 1].addEventListener("click", () => {
            if ((currentPage + 3) < paginationNavs.length) goToPage(currentPage+1);
        });
        for (let i = 1; i < paginationNavs.length - 1; i++) {
            paginationNavs[i].addEventListener("click", (e) => {
                currentPage = parseInt(e.srcElement.id.substring(5, 6)) - 1;
                goToPage(currentPage);
            })
        }
    }
}

function goToPage(pageId) {
    pages = document.getElementsByClassName("result-page");
    for (let i = 0; i < pages.length; i++) {
        pages[i].style.display = "none";
    }
    pages[pageId].style.display = "block";
    currentPage = pageId;
}