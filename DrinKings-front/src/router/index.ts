import { createRouter, createWebHistory } from 'vue-router';
// import Home from '@/views/Home.vue'; // Example component
import SignUp from '@/views/SignUp.vue';
// import Home from '@/views/Home.vue';
// import path from 'path';

const routes = [
  // {
  //   path: '/', // URL path
  //   name: 'Home', // Route name (optional)
  //   component: Home, // Component to render
  // },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp,
  },
//   {
//     path: '/dashboard',
//     component: Dashboard,
//     children: [
//       { path: 'profile', component: Profile },
//       { path: 'settings', component: Settings },
//     ],
//   },
];

const router = createRouter({
  history: createWebHistory(), // Use HTML5 history mode
  routes,
});

// router.beforeEach((to, from, next) => {
//     const isAuthenticated = !!localStorage.getItem('authToken');
//     if (to.meta.requiresAuth && !isAuthenticated) {
//       next('/login');
//     } else {
//       next();
//     }
//   });

export default router;
