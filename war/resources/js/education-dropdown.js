$(document).ready(function() {
    getUniv(); 
 });

function getUniv(){
 $.get("ajax/university.html",
                function(data) {
                   $("#univ").html(data);
                }   
           );
    }

function getFact() {
	$.post("ajax/faculty.html", {
		"universityId" : $("#univ").val()
	}, function(data) {
		$("#fact").html(data);
		getDept();
	});
}

function getDept() {
	$.post("ajax/department.html", {
		"facultyId" : $("#fact").val()
	}, function(data) {
		$("#dept").html(data);
	});
}