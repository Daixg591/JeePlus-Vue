<template>
<div class=" bg-white">
  <FullCalendar defaultView="dayGridMonth" locale="zh-cn" :header="{
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
      }"
       schedulerLicenseKey='GPL-My-Project-Is-Open-Source'
       :firstDay="1"
       :editable="true"
       :droppable="true"
       :resizeable="true"
       :selectable="true"
       @dateClick="handleDateClick"
       @eventClick="handleEventClick"
       @eventResizeStop = "handleEventResize"
      :events="calendarEvents"
       @eventDrop="handelEventDrop"
      :buttonText="buttonText"  :plugins="calendarPlugins" />
      <MyCalendarForm :start="start" :end="end" ref="myCalendarForm" @refreshDataList="refreshList"></MyCalendarForm>
</div>
</template>
<script>
  import MyCalendarForm from './MyCalendarForm'
  import FullCalendar from '@fullcalendar/vue'
  import dayGridPlugin from '@fullcalendar/daygrid'
  import timeGridPlugin from '@fullcalendar/timegrid'
  import resourceTimelinePlugin from '@fullcalendar/resource-timeline'
  import interactionPlugin from '@fullcalendar/interaction'
  export default {
    data () {
      return {
        start: new Date(),
        end: new Date(),
        calendarPlugins: [dayGridPlugin, interactionPlugin, resourceTimelinePlugin, timeGridPlugin],
        buttonText: {
          today: '今天',
          month: '月',
          week: '周',
          day: '天'
        },
        calendarEvents: [
          { title: 'event 1', date: '2019-04-01' },
          { title: 'event 2', date: '2019-04-02' }
        ]
      }
    },
    components: {
      FullCalendar,
      dayGridPlugin,
      interactionPlugin,
      MyCalendarForm
    },
    activated () {
      this.refreshList()
    },
    methods: { // 选择月份
      handleDateClick (arg) {
        this.start = this.moment(arg.dateStr).format('YYYY-MM-DD HH:mm:ss')
        this.end = this.moment(arg.dateStr).format('YYYY-MM-DD HH:mm:ss')
        this.$refs.myCalendarForm.init('add', '', this.start, this.end)
      },
      handleEventClick (info) {
        this.$refs.myCalendarForm.init('edit', info.event.id)
      },
      handelEventDrop (info) {
        this.$http.post('/myCalendar/drag', {
          id: info.event.id,
          daydiff: info.delta.days,
          minudiff: info.delta.milliseconds
        }).then(({data}) => {
          this.$message.success(data)
        })
      },
      handleEventResize (info) {
        this.$http.post('/myCalendar/resize', {
          id: info.event.id,
          daydiff: info.delta.days,
          minudiff: info.delta.milliseconds
        }).then(({data}) => {
          this.$message.success(data)
        })
      },
      refreshList () {
        this.$http.get('/myCalendar/findList').then(({data}) => {
          this.calendarEvents = data
        })
      }
    }
  }
</script>
<style lang='less'>
    //用什么插件必须引入相应的样式表，否则不能正常显示
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/daygrid/main.css';
    @import '~@fullcalendar/resource-timeline/main.css';


    #external-events {
        padding: 0 10px;
        background: #eee;
        text-align: left;
    }

    #external-events h4 {
        font-size: 16px;
        margin-top: 0;
        padding-top: 1em;
    }

    #external-events .fc-event {
        margin: 10px 0;
        cursor: pointer;
    }

    #external-events p {
        margin: 1.5em 0;
        font-size: 11px;
        color: #666;
    }

    #external-events p input {
        margin: 0;
        vertical-align: middle;
    }

</style>