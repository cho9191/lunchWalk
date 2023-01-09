


//define data array
var tabledata = [

    {id:1, courseName:"메인 1코스", courseCount:12, courseLength:"1.5", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 2코스", courseCount:5, courseLength:"2.1", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 3코스", courseCount:20, courseLength:"3", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 4코스", courseCount:10, courseLength:"0.8", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 5코스", courseCount:0, courseLength:"1", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}

];



var table = new Tabulator("#example-table123", {
    data:tabledata,           //load row data from array
    layout:"fitColumns",      //fit columns to width of table
    responsiveLayout:"hide",  //hide columns that dont fit on the table
    addRowPos:"top",          //when adding a new row, add it to the top of the table
    history:true,             //allow undo and redo actions on the table
    pagination:"local",       //paginate the data
    paginationSize:7,         //allow 7 rows per page of data
    paginationCounter:"rows", //display count of paginated rows in footer
    movableColumns:true,      //allow column order to be changed
    initialSort:[             //set the initial sort order of the data
        {column:"name", dir:"asc"},
    ],
    columnDefaults:{
        tooltip:true,         //show tool tips on cells
    },
    columns:[                 //define the table columns
        {title:"코스명", field:"courseName", width:90, editor:"input"},
        {title:"산책 횟수", field:"courseCount", width:90, hozAlign:"left", formatter:"progress", editor:true},
        {title:"코스 길이", field:"courseLength", width:95, editor:"select", editorParams:{values:["male", "female"]}},
        {title:"코스 만족도", field:"courseSatisfy", formatter:"star", hozAlign:"center", width:100, editor:true},
        {title:"코스 등록일", field:"insDtm", width:90, sorter:"date", hozAlign:"center"},
        {title:"만든이", field:"courseCreateUserName", hozAlign:"center", formatter:"input", editor:false},
    ],
});






$( document ).ready(function() {
    console.log( "main ready123" );
});