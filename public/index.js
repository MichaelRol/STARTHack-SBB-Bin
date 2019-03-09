Vue.component('my-component', {
    components: {
      QrcodeStream,
      QrcodeDropZone,
      QrcodeCapture
    },
  
    // ...
})
window.onload = function () {
    new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue.js!'
        }
    })
}