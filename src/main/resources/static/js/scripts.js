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
    query = document.getElementById("query").value;
    url = "http://localhost/api/newsletter/search?field=content&top=3&q=" + query;
    // console.log(url);
    httpGetAsync(url, showResult);
}

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
        }
        
    ]
;

function showResult(data) {
    searchResults = document.getElementById("search-results");
    searchResults.innerHTML = '';
    for (let i = 0; i < data.length; i++){
        searchResults.innerHTML += `<div class="row py-3">
                                        <a class="text-decoration-none text-dark" href="${data[i].document.link}" target="_blank">
                                            <p>${data[i].document.link}</p>
                                            <h2>${data[i].document.title}</h2>
                                        </a>
                                        <h5>Author - Date - ...</h5>
                                        <p>${data[i].document.content.substring(0,300)}...</p>
                                    </div>`
    }
}

showResult(data);

function renderResult(data) {
    
}