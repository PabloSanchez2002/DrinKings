<script setup lang="ts">
// import HelloWorld from './components/HelloWorld.vue'
import { ref } from 'vue'
import { Toaster } from '@/components/ui/toast'
import apiClient from '@/services/apiClient'
const isDarkMode = ref(true)


import { useToast } from '@/components/ui/toast/use-toast'
const toast = useToast()

// setTimeout(() => {
apiClient.get('/test')
	.then((response) => {
		if (response.status === 200) {
			toast.toast({
				title: 'Servidor en linea 🚀​🤑​🤖​​',
				duration: 2000,
			})
		} else {
			toast.toast({
				title: 'Servidor offline ',
				description: response.data.message || 'Servidor offline 😢',
				variant: 'destructive',
			})
		}
	})
	.catch((error) => {
		toast.toast({
			title: 'Error',
			description: error.response?.data?.message || error.response?.data || 'Servidor offline 😢',
			variant: 'destructive',
		})
	})
// }, 3000)

</script>

<template>
	<div :class="{ dark: isDarkMode }" class="min-h-screen bg-background text-foreground">
		<Toaster />
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
