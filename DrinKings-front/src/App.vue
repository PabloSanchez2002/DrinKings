<script setup lang="ts">
// import HelloWorld from './components/HelloWorld.vue'
import { ref } from 'vue'
import { Button } from '@/components/ui/button'

const isDarkMode = ref(true)

</script>

<template>
  <div :class="{ dark: isDarkMode }" class="min-h-screen bg-background text-foreground">
    <router-view />
  </div>
</template>

<!-- <style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style> -->
