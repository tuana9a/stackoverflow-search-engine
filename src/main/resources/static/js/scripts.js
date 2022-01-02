// const test = 
// [
//     {
//         link: "https://google.com",
//         title: "Test Title",
//         content: "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eaque atque consectetur vitae assumenda, ipsum repellat distinctio suscipit quod rerum perferendis?",
//         tokenizedString: " [i 1132 218] [invit 43 33] [particip 145 82] [latest 38 26] [herd 4 2] [code 419 153] [podcast 473 158] [fun 93 56] [on 775 279] [me 259 118] [becaus 300 164] [i’v 77 54] [known 30 29] [four 57 46] [host 107 65] [program 329 141] [jon 58 22] [gallowai 2 2] [kevin 20 14] [dent 2 2] [k 6 6] [scott 23 8] [allen 4 4] [scott 23 8] [koon 2 2] [through 209 135] [blog 213 123] [sinc 189 118] [forev 16 15] [eventu 34 30] [meet 63 46] [most 410 199] [them 477 210] [person 161 102] [all 860 288] [blog 213 123] [predat 3 3] [mine 14 13] [year 704 211] [i’d 31 24] [almost 98 78] [sai 166 110] [we 3669 361] [were 436 166] [blog 213 123] [buddi 3 2] [you 2680 351] [could 183 109] [call 214 154] [peopl 680 221] [blog 213 123] [buddi 3 2] [which 541 241] [i 1132 218] [don’t 362 180] [think 298 176] [you 2680 351] [can 1185 317] [ani 318 174] [rate 96 32] [i 1132 218] [go 384 185] [quit 83 71] [bit 151 104] [technic 127 76] [depth 13 10] [stack 2285 363] [overflow 1686 318] [some 633 256] [featur 298 125] [we 3669 361] [have 1580 339] [yet 62 55] [implement 52 35] [have 1580 339] [been 439 211] [hotli 1 1] [request 108 56] [definit 44 36] [worth 40 35] [listen 165 122] [you 2680 351] [want 530 204] [go 384 185] [deep 30 27] [thank 159 103] [have 1580 339] [me 259 118] [oh 29 23] [listen 165 122] [past 103 77] [end 136 95] [littl 124 94] [audio 185 97] [easter 9 3] [egg 14 8] [sort 81 63] [fun 93 56]"
//     }
// ]

var currentPage = 0;
document.getElementById("loading-page").style.display = "none";

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(JSON.parse(xmlHttp.responseText), 10);
        else if (xmlHttp.readyState == 4 && xmlHttp.status == 400)
            emptyResult();
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
    document.getElementById("home-page").style.display = "none";
    httpGetAsync(url, showResult);
    document.getElementById("search-results").innerHTML = '';
    document.getElementById("pagination").innerHTML = '';
    document.getElementById("loading-page").style.display = "block";
    // showResult(test, 4);
}

function emptyResult(){
    document.getElementById("loading-page").style.display = "none";
    document.getElementById("search-results").innerHTML = `<p class="text-center">No blogs related to this keyword founded!</p>`;
}

function showResult(data, articlePerPage = 5) {
    console.log(data);
    if(data.length == 0) {
        emptyResult();
        return ;
    }
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
        var tokenizedArray = data[i].tokenizedString.trim().substring(1, data[i].tokenizedString.length-2).split("] [");
        var tokenizedModalBody = '';
        for (let j = 0; j < tokenizedArray.length; j++) {
            tokenizedModalBody += `
                <div class="row border-bottom">
                    <div class="col">${tokenizedArray[j].split(' ')[0]}</div>
                    <div class="col">${tokenizedArray[j].split(' ')[1]}</div>
                    <div class="col">${tokenizedArray[j].split(' ')[2]}</div>
                </div>
            `
        }

        
        if (i % articlePerPage == 0) searchResults.innerHTML += `<div class="result-page" id="result-page-${(i / articlePerPage) + 1}"></div>`
        document.getElementsByClassName('result-page')[document.getElementsByClassName('result-page').length -1].innerHTML += `
            <div class="row py-3">
                <a class="text-decoration-none text-dark" href="${data[i].link}" target="_blank">
                    <p>${data[i].link}</p>
                    <h2>${data[i].title}</h2>
                </a>
                <p>${data[i].content.substring(0,300)}...</p>
                <p class="fst-italic" type="button" data-bs-toggle="modal" data-bs-target="#tokenizedModal${i}">Tokenized Result</p>
                <div class="modal fade" id="tokenizedModal${i}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="tokenizedModal${i}">Modal title</h5>
<!--                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">-->
                                <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="col text-center">
                                    <div class="row border-bottom">
                                        <div class="col"><strong>Word</strong></div>
                                        <div class="col"><strong>Term frequence</strong></div>
                                        <div class="col"><strong>Doc frequence</strong></div>
                                    </div>
                                    ${tokenizedModalBody}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`
        document.getElementById("loading-page").style.display = "none";
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