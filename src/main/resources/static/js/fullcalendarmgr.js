// $ is a shorthand for $(document).ready, the jQuery event that tells when the document can be safely manipulated

$(function () {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('personid');

  $('#calendar').each(function () {
    var calendar = $(this);
    calendar.fullCalendar({
      header: {
        left: 'prev, next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
      },
      selectable: true,
      selectHelper: true,
      editable: true,
      eventLimit: true,
      events: '/days/' + id,
      select: function (startDate, endDate, allday) {
        let title = prompt('Enter Event Title');
        if (title) {
          let start = $.fullCalendar.formatDate(startDate, 'YYYY-MM-DD HH:mm');
          let end = $.fullCalendar.formatDate(endDate, 'YYYY-MM-DD HH:mm');
          $.ajax
            ({
              type: 'POST',
              dataType: 'text',
              contentType: 'application/json',
              url: '/Calendar',
              data: JSON.stringify({ "eventTitle": title, "personId": id, "theStartDate": start, "theEndDate": end }),
              success: function () {
                calendar.fullCalendar('refetchEvents');
              }
            })
        }
      },
      eventResize: function (event) {
        let start = $.fullCalendar.formatDate(event.start, 'YYYY-MM-DD HH:mm');
        let end = $.fullCalendar.formatDate(event.end, 'YYYY-MM-DD HH:mm');
        let title = event.title;
        let eventId = event.id;
        $.ajax({
          type: 'POST',
          dataType: 'text',
          contentType: 'application/json',
          url: '/Calendar',
          data: JSON.stringify({ "eventTitle": title, "personId": id, "theStartDate": start, "theEndDate": end, "eventId": eventId }),
          success: function () {
            calendar.fullCalendar('refetchEvents');
          }
        })
      },
      eventDrop: function (event, delta, revertFunc) {
        let start = $.fullCalendar.formatDate(event.start, 'YYYY-MM-DD HH:mm');
        let end = $.fullCalendar.formatDate(event.end, 'YYYY-MM-DD HH:mm');
        let title = event.title;
        let eventId = event.id;
        $.ajax({
          type: 'POST',
          dataType: 'text',
          contentType: 'application/json',
          url: '/Calendar',
          data: JSON.stringify({ "eventTitle": title, "personId": id, "theStartDate": start, "theEndDate": end, "eventId": eventId }),
          success: function () {
            calendar.fullCalendar('refetchEvents');
          }
        })
      },
      eventClick: function (event, jsEvent, view) {
        if (confirm("This will remove the event. Are you Sure?")) {
          let eventId = event.id;
          $.ajax({
            type: 'DELETE',
            dataType: 'text',
            contentType: 'application/json',
            url: '/deleteDay',
            data: JSON.stringify({ "eventId": eventId }),
            success: function () {
              calendar.fullCalendar('refetchEvents');
            }
          })
        }
      }
    })

  });

  // page is now ready, initialize the calendar...

  $('#calendar').fullCalendar({
    // put your options and callbacks here
    /*   dayClick: function () {
        openForm();
      }, */


    dayClick: function (date, jsEvent, view) {

      //alert('Clicked on: ' + date.format());

      //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);

      //alert('Current view: ' + view.name);

      // change the day's background color just for fun
      $(this).css('background-color', 'lightcoral');
      $('#calendar').fullCalendar('renderEvent', new Event("test", date));
      $('#calendar').fullCalendar.events.push({
        start: '2018-12-01',
        end: '2018-12-02',
        overlap: false,
        rendering: 'background',
        color: '#ff9f89'
      });

    },
    eventClick: function (calEvent, jsEvent, view) {

      alert('Event: ' + calEvent.title);
      alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
      alert('View: ' + view.name);

      // change the border color just for fun
      $(this).css('border-color', 'red');

    }

  })


});
