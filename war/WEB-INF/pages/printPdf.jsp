<%-- 
    Document   : displayAppFormView
    Created on : 06.04.2013, 0:55:39
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% pageContext.setAttribute("curLevel", 1); %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Анкета ${columnFields.getAppId()}</title>
        <link href="resources/css/template.css" rel="stylesheet">
         <jsp:include page="resources.jsp" />
    </head>
    <body>

<div class="appForm-format">

        <div class="row ">
  <div class="span12">
    <div class="row span10">
      <div class="span3 thumbnail" style="width: 150px; height: 200px;">
        
            <img style="width: 150px; height: 200px;" src="http://www.placehold.it/120x160/EFEFEF/AAAAAA&text=Photo" alt="">
      </div>
      <div class="span6">    
          
        <h3><strong class="title-color">${columnFields.getFio()} </strong></h3>
        <p>
            № анкеты: ${columnFields.getAppId()}
        </p>
        <p>
          email: ${columnFields.getEmail()}
        </p>
        <p>
          Номер телефона: ${columnFields.getPhoneNumber()}
        </p>
        <p>
           Как узнал о центре?: ${columnFields.getAdvertisingName()} 
           <c:if test="${columnFields.getAdvertisingComment()!=null}">
               (${columnFields.getAdvertisingComment()})
           </c:if>
        </p>
      </div>
    </div>

  </div>
</div>
           <div class="align-center">
                <h4><strong class="title-color">ОБРАЗОВАНИЕ</strong></h4>
                </div>
           
           <p>
          ВУЗ: ${columnFields.getUniversityName()}
        </p>
        <p>
          Факультет: ${columnFields.getFacultyName()}
        </p>
        <p>
          Кафедра: ${columnFields.getDepartmentName()}
        </p>
        <p>
          Курс: ${columnFields.getCourse()}
        </p>
        <p>
          Год окончания: ${columnFields.getGraduated()}
        </p>
  <%-- <c:forEach items="${columnFields.getCf()}" var="contactMap" varStatus="status">
            <c:if test="${contactMap.getLevel()==1}">
                <div class="align-center">
                <h4><strong class="title-color">${contactMap.getName()}</strong></h4>
                </div>
            </c:if>
            <c:if test="${contactMap.getLevel()!=1}">
                ${contactMap.getName()}
            </c:if>
             ${contactMap.getValue()}</br>
    </c:forEach>--%>
  
  
   <c:forEach items="${columnFields.getCf()}" var="contactMap" varStatus="status">
       
            <c:if test="${contactMap.getLevel()==1}">
                <div class="align-center">
                <h4><strong class="title-color">${contactMap.getName()}</strong></h4>${contactMap.getValue()}
                </div>
            </c:if>
            <c:if test="${contactMap.getLevel()!=1}">
                <c:forEach var="i" begin="${contactMap.getLevel()}" end="${curLevel-1}"></ul></c:forEach>
                <c:forEach var="i" begin="${curLevel+1}" end="${contactMap.getLevel()}"><ul></c:forEach>
                    <c:set var="curLevel" value="${contactMap.getLevel()}" />
                    <li>${contactMap.getName()}${contactMap.getValue()}</li>
            </c:if>
         
    </c:forEach>
           
      <div class="align-center">
          <h4><strong class="title-color">СОГЛАШЕНИЕ</strong></h4>
      </div>  
             <p>
                 Я даю согласие на хранение, обработку и использование моих данных с целью 
                 возможного обучения и трудоустройства в компании НЕТКРЕКЕР на данный момент и в будущем 
             </p>   
             <div class="line"></div>
             
       <div class="align-center">  
        <h4><strong class="title-color">СОБЕСЕДОВАНИЕ</strong></h4>
        <p>(заполняется интервьюерами)</p>
          </div>    
             
        <table class="table-int">
      <tr>
          <th>HR или Аналитик</th>
          <th>Разработчик</th>
      </tr>
      <tr>
          <td></td>
          <td></td>
      </tr>
  </table>     
             
        </div>
  
    </body>
</html>