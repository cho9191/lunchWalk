
var userName = '';
var courseList = [];


//define data array
tabledata = [
/*
    {id:1, courseName:"메인 1코스", courseCount:12, courseLength:"1.5", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 2코스", courseCount:5, courseLength:"2.1", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 3코스", courseCount:20, courseLength:"3", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 4코스", courseCount:10, courseLength:"0.8", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
    ,{id:1, courseName:"메인 5코스", courseCount:0, courseLength:"1", courseSatisfy:5,  insDtm:"2023-01-01", courseCreateUserName:"홍길동"}
*/
];



var table = new Tabulator("#courseTable", {
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
        {title:"코스명", field:"courseName", width:150, editor:"input"},
        {title:"산책 횟수", field:"courseExecuteCnt", width:100, hozAlign:"left", formatter:"progress", editor:true},
        {title:"코스 길이 (km)", field:"courseLength", width:105, editor:"select"},
        {title:"코스 만족도", field:"courseSatisfyPoint", formatter:"star", hozAlign:"center", width:100, editor:true},
        {title:"만든이", field:"courseCreateUserName", hozAlign:"center", formatter:"input", editor:false},
        {title:"코스 등록일", field:"courseInsDtm", width:90, sorter:"date", hozAlign:"center"},
    ],
});



function courseSearch(){


    var data = {
    }

    $.ajax({
        type: "POST",
        url: "/course/search",
        data: JSON.stringify(data),
        //data: data,
        //dataType: 'application/json',
        contentType: 'application/json',
        success:function(data){
            console.log(data);
            table.setData(data);

            makeCourseList(data);

        }
    });
}


function makeCourseList(data){
    var selCourseList = $('#selCourseList');

    for(var i=0; i<data.length; i++){
        selCourseList.append("<option value="+data[i].courseId+">"+data[i].courseName+"</option>"); //ul_list안쪽에 li추가
    }
}




function onAddEventListener(){

    //코스 추가 버튼
    $('#btnAddCourse').click(function (){

        var data = {
            courseName : $('#courseName').val(),
            courseLatitude : '123',
            courseLongitude : '456',
            courseKcal : $('#courseKcal').val(),
            courseLength : $('#courseLength').val()
        }

        console.log('data');
        console.log(data);

        $.ajax({
            type: "POST",
            url: "/course/create",
            data: JSON.stringify(data),
            //data: data,
            //dataType: 'application/json',
            contentType: 'application/json',
            success:function(data){
                $('#addCourseModal').modal("hide");
            },
            error: function (request, status, error) {
                //console.log("code: " + request.status)
                //console.log("message: " + request.responseText)
                //console.log("error: " + error);
                alert('에러 발생!');
            }
        });
    });


    //코스 조회 버튼
    $('#btnCourseSearch').click(function (){
        courseSearch();
    });

    //코스 추가 모달창
    $('#btnAddCourseModal').click(function (){
        $('#addCourseModal').modal("show");
    });

    //라디오 버튼 제어
    $("input[name='optradio']").change(function (){
        //alert('이벤트 감지 : '+$("input[name='optradio']:checked").val());
        var isAutoYn = $("input[name='optradio']:checked").val();

        if('Y' == isAutoYn){
            $('#selCourseList').attr('disabled', true);
            $('#selCourseList').attr('hidden', true);
            $('#labelSelCourseList').attr('hidden', true);

        }else{
            $('#selCourseList').attr('disabled', false);
            $('#selCourseList').attr('hidden', false);
            $('#labelSelCourseList').attr('hidden', false);
        }

    });

    //코스 선택 버튼
    $('#btnCourseCnfm').click(function () {

        console.log('111');

        var data = {
            isAutoYn : $("input[name='optradio']:checked").val()
        }

        $.ajax({
            type: "POST",
            url: "/course/confirm",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success:function(data){
                //$('#addCourseModal').modal("hide");
                console.log(data);
            },
            error: function (request, status, error) {
                alert('에러 발생!');
            }
        });

    });


    //(임시)코스 히스토리 생성
    //코스 조회 버튼
    $('#btnTmpCreHist').click(function (){

        var data = {
            courseId : $('#tmpCourseId').val()
        }

        console.log(data);

        $.ajax({
            type: "POST",
            url: "/course/tmp/hist",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success:function(data){
                console.log(data);

            }
        });

    });

}

function getDefaultValue(){

    console.log('test11');

    $.ajax({
        type: "POST",
        url: "/user/info",
        //data: data,
        dataType: 'json',
        success:function(data){

            console.log(data);
            userName = data.userName;
            $('#userName').val(userName);
            console.log('1userName : '+userName)


        }
    });

    courseSearch();
}



$( document ).ready(function() {

    getDefaultValue();

    onAddEventListener();

});