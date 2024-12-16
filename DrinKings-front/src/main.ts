import { createApp } from 'vue'
// import './style.css'
import './assets/index.css'
import App from './App.vue'
import router from './router'; // Import the router

// createApp(App).mount('#app')

const app = createApp(App);
app.use(router); // Register the router
app.mount('#app');