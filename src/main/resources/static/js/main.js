
var userName = '';
var bTodayCourse = false;


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
        contentType: 'application/json',
        success:function(data){
            console.log(data);
            table.setData(data);
            makeCourseList(data);
        }
    });
}

function todayCourse(){
console.log('todayCourse start!');
    $.ajax({
        type: "POST",
        url: "/course/today",
        contentType: 'application/json',

        success:function(data){
            if(data == ''){
                bTodayCourse = false;
                setTodayCourseHeasder(false);
            }else{
                bTodayCourse = true;
                setTodayCourseHeasder(true);
                setTodayCourseTable(data);
                console.log('before data : ');
                console.log(data);
                getMapUrl(data.courseId);
            }
        }
    });
}


function makeCourseList(data){
    var selCourseList = $('#selCourseList');

    for(var i=0; i<data.length; i++){
        selCourseList.append("<option value="+data[i].courseId+">"+data[i].courseName+"</option>");
    }
}

function setTodayCourseHeasder(today){
    console.log('today check!');
    console.log(today);
    var header = '';
    if(today){
            header = '오늘의 산책 코스 ('+getNowDate()+')';
    }else{
        header = '산책 코스 확정 전입니다. /n오늘의 산책 코스를 선택하세요!';
    }
    document.getElementById("todayCourseHeader").innerHTML = header;
}

function setTodayCourseTable(data){

    console.log('setTodayCourseTable start!');
    console.log(data);

    var html = '';
    html += '<tr>';
    html += '<td>'+data.courseName+'</td>';
    html += '<td>'+data.courseLength+'</td>';
    html += '<td>'+data.courseKcal+'</td>';
    html += '<td>'+data.courseCreateUserName+'</td>';
    html += '</tr>';

    $("#cnfmCourseTable").empty();
    $("#cnfmCourseTable").append(html);

}

function getNowDate(){
    const date = new Date();

    var YYYY = date.getFullYear();
    var MM = date.getMonth()+1;
          MM = String(MM).padStart(2, '0');
    var DD = date.getDate();
          DD = String(DD).padStart(2, '0');

    console.log('MM : '+MM)
return YYYY+'-'+MM+'-'+DD;
}


function getMapUrl(courseId) {
    console.log('getMapUrl start!');

    var data = {
        courseId : courseId
    }

    $.ajax({
        type: "POST",
        url: "/course/map/url",
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (data) {
            console.log('getMapUrl : ');
            console.log(data);
            document.getElementById("imgCourseImgStr").src = data;
        }
    });
}


function getWalkHist() {
    console.log('getWalkHist start!');



    $.ajax({
        type: "POST",
        url: "/course/walk/hist",
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            console.log(data.length);
            console.log('getWalkHist end!');

            var html = '';
            for(var i=0; i<data.length; i++){
                html += '<tr>';
                html += '<td>'+data[i].courseDtm+'</td>';
                html += '<td>'+data[i].courseName+'</td>';
                html += '<td>'+data[i].courseLength+'</td>';
                html += '<td>'+data[i].courseKcal+'</td>';
                html += '<td>'+data[i].courseAttendee+'</td>';
                html += '</tr>';
            }

            $("#walkHistoryTable").empty();
            $("#walkHistoryTable").append(html);


        }
    });
}


//이벤트 등록 영역
function onAddEventListener(){

                //코스 추가 버튼
                $('#btnAddCourse').click(function (){

                    var data = {
                        courseName : $('#courseName').val(),
                        courseLatitude : $('#courseLatitude').val(),
                        courseLongitude : $('#courseLongitude').val(),
                        courseKcal : $('#courseKcal').val(),
                        courseLength : $('#courseLength').val()
                    }

                    $.ajax({
                        type: "POST",
                        url: "/course/create",
                        data: JSON.stringify(data),
                        //data: data,
                        //dataType: 'application/json',
                        contentType: 'application/json',
                        success:function(data){
                            $('#addCourseModal').modal("hide");
                            //setTodayCourseHeasder(bTodayCourse);
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

                    var data = {
                        isAutoYn : $("input[name='optradio']:checked").val()
                    }

                    $.ajax({
                        type: "POST",
                        url: "/course/confirm",
                        data: JSON.stringify(data),
                        contentType: 'application/json',
                        success:function(data){
                            todayCourse();
                        },
                        error: function (request, status, error) {
                            alert('에러 발생!');
                        }
                    });

                });
    }



function getDefaultValue(){

    $.ajax({
        type: "POST",
        url: "/user/info",
        //data: data,
        dataType: 'json',
        success:function(data){

            console.log(data);
            userName = data.userName;
            $('#userName').val(userName);
            console.log('1userName : '+userName);
        }
    });
    courseSearch();
    todayCourse();
    getWalkHist();
}


$( document ).ready(function() {

    getDefaultValue();
    onAddEventListener();
});