const data = 
    [
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 1"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 2"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 3"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 4"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 5"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 6"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 7"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 8"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 9"
            },
            "tokenizedString": "apple, banana, orange"
        },
        {
            "document": {
                "link": "https://facebook.com",
                "content": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere inventore quo laboriosam assumenda laudantium ad veniam consectetur debitis quia vel ipsum eveniet magni, deserunt architecto nostrum ipsam deleniti neque obcaecati quam quaerat illo voluptatem tempore hic soluta. Deserunt consequatur at nam dolorum expedita, eius commodi animi quidem, molestiae similique non.",
                "title": "This is an article 10"
            },
            "tokenizedString": "apple, banana, orange"
        }
        
    ]
;

var currentPage = 0;

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}

async function searchRequest() {
    showResult(data, 4);
    // query = document.getElementById("query").value;
    // url = "http://localhost/api/newsletter/search?field=content&top=3&q=" + query;
    // // console.log(url);
    // httpGetAsync(url, showResult);
}

function showResult(data, articlePerPage = 5) {
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
                <a class="text-decoration-none text-dark" href="${data[i].document.link}" target="_blank">
                    <p>${data[i].document.link}</p>
                    <h2>${data[i].document.title}</h2>
                </a>
                <h5>Author - Date - ...</h5>
                <p>${data[i].document.content.substring(0,300)}...</p>
            </div>`
    }

    goToPage(0);

    paginationNavs = document.getElementsByClassName('page-link');
    if (paginationNavs.length > 1) {
        paginationNavs[0].addEventListener("click", () => {
            if (currentPage > 0) goToPage(--currentPage);
        });
        paginationNavs[paginationNavs.length - 1].addEventListener("click", () => {
            if (currentPage + 1 > paginationNavs.length) goToPage(++currentPage);
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
}

function renderResult(data) {
    
}