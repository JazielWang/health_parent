<template>
  <div class="map-container">
  </div>
</template>
<script>
export default {
  name: 'Map',
  props: {
    options: Object
  },
  data () {
    return {
      $map: null
    }
  },
  mounted () {
    // $el是当前组件根元素，可以直接用来初始化高德地图实例
    this.$map = new AMap.MAP(this.$el, this.options)
  },
  methods: {
  },
  beforeDestroy () {
    // 组件销毁前可销毁地图实例
    this.$map && this.$map.destroy()
  }
}
</script>