/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function toggleedit(selector)
{
    $(selector).toggle();
    return false;
}
/*
function submitDelete()
{
    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("action", "delTemplate.html");
    for (var i in $(':checkbox[name^=template]'))
        form.appendChild(i);
    form.submit();
    return false;
}*/

function cbToggle()
{
    var maincb = $(':checkbox#one');
    var others = $(':checkbox').not('#one').not(':disabled');
    others.prop('checked', maincb.prop('checked'));
    return false;
}

function submitDelete(dAction,dSelector)
{
    var form = $("<form></form>");
    $(dSelector).clone().appendTo(form);
    form.attr("method","POST");
    form.attr("action",dAction);
    form.attr("style","display: hidden;");
    $('body').append(form);
    form.submit();
    /*
    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("action", dAction);
    for (var i in $(dSelector))
        if (i!=null)
        form.appendChild(i);
    form.submit();*/
    return false;
}
/*
function checkAll(obj) 
{
  'use strict';
  // Получаем NodeList дочерних элементов input формы: 
  var items = obj.form.getElementsByTagName("input"), 
      len, i;
  // Здесь, увы цикл по элементам формы:
  for (i = 0, len = items.length; i < len; i += 1) {
    // Если текущий элемент является чекбоксом...
    if (items.item(i).type && items.item(i).type === "checkbox") {
      // Дальше логика простая: если checkbox "Выбрать всё" - отмечен            
      if (obj.checked) {
        // Отмечаем все чекбоксы...
        items.item(i).checked = true;
      } else {
        // Иначе снимаем отметки со всех чекбоксов:
        items.item(i).checked = false;
      }       
    }
  }
}*/

function number_control()
{
    //Вызывается когда вводятся символы в поле с id quantity
    $("#quantity").keypress(function (e)  
    { 
      //Если символ - не цифра, ввыодится сообщение об ошибке, другие символы не пишутся
      if (e.shiftKey || (e.keyCode < 48 || e.keyCode > 57) && (e.keyCode < 96 || e.keyCode > 105))
      {
        return false;
      }  

    });
  }
  
  function ajax_result()
  {
    $.ajax(
    {
        url: 'resultAjax.html',             // указываем URL и
        dataType : "json",                     // тип загружаемых данных
        success: function (data, textStatus) 
        { // вешаем свой обработчик на функцию success
            $("#studentTable").empty();
            $.each(data.students, function(i, val) 
            {    // обрабатываем полученные данные
                var tr = $("<tr></tr>");
                $("<td></td>").append(val.r).appendTo(tr);
                $("<td></td>").append(val.appId).appendTo(tr);
                $("<td></td>").append(val.fio).appendTo(tr);
                $("<td></td>").append(val.email).appendTo(tr);
                $("<td></td>").append(val.avgMark).appendTo(tr);
                $("<td>nya</td>").appendTo(tr);
                tr.appendTo("#studentTable");
            });
        },
        error: function ( jqXHR, textStatus, errorThrown )
        {
            console.log(errorThrown);
        }
    }
    );

  }