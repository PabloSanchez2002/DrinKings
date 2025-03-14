import { createRouter, createWebHistory } from 'vue-router';
import SignUp from '@/views/SignUp.vue';
import Access from '@/views/Access.vue';
import Login from '@/views/Login.vue';
import Home from '@/views/Home.vue';
import Landing from '@/views/Landing.vue';
import Profile from '@/views/Profile.vue';
import Liga from '@/views/Liga.vue';

const routes = [
  {
    path: '/:pathMatch(.*)*', // Catch all route
    redirect: '/home', // Redirect to /home
  },
  {
    path: '/', // URL path
    name: 'Home', // Route name (optional)
    redirect: '/home', // Always redirect to /home
    component: Home, // Component to render
    meta: { requiresAuth: true },
    children: [
      { path: 'home', component: Landing },
      { path: 'profile', component: Profile },
      { path: 'league/:id', component: Liga },
    ]
  },
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

router.beforeEach((to, _from, next) => {
    const isAuthenticated = !!localStorage.getItem('auth_token');
    if (to.meta.requiresAuth && !isAuthenticated) {
      next('/access/login');
    } else {
      next();
    }
  });

export default router;
