import { createRouter, createWebHistory } from 'vue-router';
import SignUp from '@/views/SignUp.vue';
import Access from '@/views/Access.vue';
import Login from '@/views/Login.vue';
import Home from '@/views/Home.vue';
// import path from 'path';

const routes = [
  {
    path: '/home', // URL path
    name: 'Home', // Route name (optional)
    component: Home, // Component to render
  },
  // {
  //   path: '/signup',
  //   name: 'SignUp',
  //   component: SignUp,
  // },
  {
    path: '/access',
    component: Access,
    children: [
      { path: 'signup', component: SignUp },
      { path: 'login', component: Login },
    ],
  },
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
