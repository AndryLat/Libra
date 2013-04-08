<%-- 
    Document   : TemplateCreator
    Created on : 24.03.2013, 21:02:57
    Author     : MorrisDeck
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
    <!--<![endif]-->
    <head>
        <jsp:include page="resources.jsp" />
        <script type="text/javascript" src="resources/js/tiny_mce/tiny_mce.js"></script>
        <title>Libra: Конструктор шаблонов</title>
    </head>

    <body>
        <div class="navmenu">
            <jsp:include page="navbar.jsp" />
        </div>

        <div class="container-fluid">
            <div class="row-fluid">

                <div class="sidebar">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="span9">

                    <script type="text/javascript">
                        tinyMCE.init({
                            // General options
                            mode: "textareas",
                            theme: "advanced",
                            plugins: "autolink,lists,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
                            // Theme options
                            theme_advanced_buttons1: "newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
                            theme_advanced_buttons2: "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
                            theme_advanced_buttons3: "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
                            theme_advanced_toolbar_location: "top",
                            theme_advanced_toolbar_align: "left",
                            theme_advanced_statusbar_location: "bottom",
                            theme_advanced_resizing: true,
                            // Skin options
                            skin: "o2k7",
                            skin_variant: "silver",
                        });
                    </script>

                    <form:form class="form-horizontal" method="POST" action="/Libra/takeTemplate.html" name="textForm">
                        <div class="alert">
                            <strong>Инструкция:</strong><br> Шаблон создается в виде хтмл страницы. Данную хтмл страницу вы можете верстать так, как вы хотите.
                            При отправке сообщения, шаблонизатор может подставить нужные вам значения. Для того, чтобы это сделать, в шаблоне надо делать вставки, похожие на 
                            JSP код: $&#123;название переменной&#125;. Чтобы придать этой переменной значение, вам нужно создать HashMap и вставлять туда значения ("ключ (название переменной в шаблоне)", ее значение).
                            <br><strong>Пример:</strong> 
                            <div class="alert alert-info">
                                <strong>Код шаблона:</strong> &lt;h1>Привет, $&#123;user&#125;!&lt;/h1><br> <strong>Название шаблона:</strong> "Пример"
                                <br><strong>Код Java:</strong> /*some code*/ Map model = new HashMap(); model.put("user","Вася!"); SendMailServise.sendMail("test@mail.ru",model,"Пример"); /*some code*/
                                <br> <strong>Результат:</strong> Привет, Вася!!
                            </div>
                        </div>
                        <center><form:textarea path="content" rows="5" cols="30" style="width:100%; height:60%"/></center>
                        <br>
                        <center><table border="0">
                                <tbody>
                                    <tr>
                                        <td align="right"><label class="label" for="templateName">Название шаблона:</label></td>
                                        <td align="left"><form:input type="text" path="templateName"/><span class="label label-info">Обязательно!</span></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><label class="label" for="templateName">Автор:</label></td>
                                        <td align="left"><form:input type="text" path="author"/><span class="label label-info">Обязательно!</span></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><label class="label" for="templateName">Описание:</label></td>
                                        <td align="left"><form:input type="text" path="describe"/><span class="label label-info">Обязательно!</span></td>
                                    </tr>
                                </tbody>
                            </table></center>
                                    <br>
                        <center><button class="btn btn-primary" type="submit" onclick="sendToServer()" >Сохранить шаблон</button></center> 
                        </form:form>

                    <br>
                    ${Text}                 

                </div>
            </div>
        </div>
    </body>
    <script>
                        function sendToServer() {
                            span9.forms["textForm"].submit();
                        }
    </script>
</html>


