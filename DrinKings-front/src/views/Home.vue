<script setup lang="ts">
import CreateLeagueForm from '@/components/ui/CreateLeagueForm.vue'
import JoinLeagueFrom from '@/components/ui/JoinLeagueFrom.vue'
import apiClient from '@/services/apiClient'
import { Button } from '@/components/ui/button'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet'
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import { CircleUser, Beer } from 'lucide-vue-next'
import { CirclePlus, SquarePlus } from 'lucide-vue-next';
import { useRoute, useRouter } from 'vue-router'
import { jwtDecode } from 'jwt-decode';
import { ref, watch } from 'vue';
import { useToast } from '@/components/ui/toast/use-toast'

const { toast } = useToast()
const token = localStorage.getItem('auth_token');
const userName = ref('');
const isCreateDialogOpen = ref(false);
const isJoinDialogOpen = ref(false);
const isSheetOpen = ref(false);

interface League {
	id: number;
	name: string;
	// Add other properties if needed
}
const userLeagues = ref<League[]>([]);
const route = useRoute();
const router = useRouter()
const isLoading = ref(false)


watch(() => route.params.id, () => {
	reloadLeagues()
});

const reloadLeagues = () => {
	apiClient.get('/user/getLeaguesOfUser', {
		headers: {
			Authorization: `Bearer ${token}`
		}
	})
		.then(response => {
			userLeagues.value = response.data;
			console.log('User leagues fetched successfully:', userLeagues.value);
		})
		.catch(error => {
			console.error('Error fetching user leagues:', error);
		});
}


if (token) {
	// Decode the JWT
	const decodedToken: any = jwtDecode(token);

	userName.value = decodedToken?.sub;
	console.log(`Welcome, ${userName.value}`);

	// Fetch user leagues
	reloadLeagues();

} else {
	console.log('No token found');
}

const logout = () => {
	// Remove token from localStorage
	localStorage.removeItem('auth_token')

	console.log('Logged out');
	router.push('/access/login')
}

const toProfile = () => {
	console.log('Going to profile');
	router.push('/profile')
}

const goToLeague = (leagueId: number) => {
	router.push(`/league/${leagueId}`);
	isSheetOpen.value = false;
}

const createLeague = async (values: any) => {
	isLoading.value = true

	console.log('Form submitted with:', values)

	apiClient.post('/league', values, {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	}).then((response) => {
		if (response.status === 200) {
			toast({
				title: 'Éxito',
				description: 'Liga ' + response.data.name + ' creada correctamente.',
				duration: 2000,
			})
			joinLeague(response.data.shareToken.value)
			reloadLeagues()
			goToLeague(response.data.id)
			isCreateDialogOpen.value = false
		}
		else {
			toast({
				title: 'Error',
				description: response.data.message || 'Error al crear la liga',
				variant: 'destructive',
			})
		}
	}).catch(error => {
		toast({
			title: 'Error',
			description: error.response.data.message || 'No se pudo crear la liga. Inténtalo de nuevo.',
			variant: 'destructive',
		})
	}).finally(() => {
		isLoading.value = false
	})
}

const joinLeague = async (shareToken: any) => {
	// Implement the joinLeague function
	console.log('Joining league with shareToken:', shareToken)
	apiClient.post('/league/join', shareToken, {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	})
		.then((response) => {
			toast({
				title: 'Éxito',
				description: 'Unido a liga ' + response.data.name + ' correctamente.',
				duration: 2000,
			})
			reloadLeagues()
			goToLeague(response.data.id)
			isJoinDialogOpen.value = false
		})
		.catch((error) => {
			toast({
				title: 'Error',
				description: error.response.data.message || 'No se pudo unir a la liga. Inténtalo de nuevo.',
				variant: 'destructive',
			})
		})
		.finally(() => {
			isLoading.value = false
		})
}

</script>

<template>
	<div class="flex min-h-screen w-full flex-col">
		<header class="sticky top-0 flex h-16 items-center gap-4 border-b bg-background px-4 md:px-6">
			<Sheet v-model:open="isSheetOpen">
				<SheetTrigger as-child>
					<Button variant="outline" size="icon" class="shrink-0 h-12 w-12">
						<Beer class="h-10 w-10" />
					</Button>
				</SheetTrigger>
				<SheetContent side="left" class="h-full">
					<nav class="h-full flex flex-col gap-6 font-medium justify-between">
						<!-- Title Section -->
						<div class="flex flex-col gap-4">
							<div class="flex gap-2 text-2xl font-semibold">
								<Beer class="h-6 w-6" />
								<p>Tus ligas</p>
							</div>
							<!-- Ligas -->
							<div v-for="league in userLeagues" :key="league.id">
								<a href="#" class="text-muted-foreground hover:text-foreground text-xl"
									@click.prevent="goToLeague(league.id)">
									👉 {{ league.name }}
								</a>
							</div>
						</div>

						<!-- Main Content -->
						<!-- Dialog Container -->
						<div class="flex flex-col gap-4">
							<!-- DialogTrigger for Crea una liga -->
							<Dialog v-model:open="isCreateDialogOpen">
								<DialogTrigger as-child>
									<a href="#"
										class="text-muted-foreground hover:text-foreground flex items-center gap-2">
										<CirclePlus class="h-7 w-7" />
										Crea una liga
									</a>
								</DialogTrigger>
								<DialogContent
									class="w-[90%] sm:w-[80%] md:w-[70%] lg:w-[60%] max-w-none rounded-lg mx-auto p-6">
									<DialogHeader>
										<DialogTitle>Crea una nueva liga</DialogTitle>
										<DialogDescription>
											Complete los campos para crear una nueva liga.
										</DialogDescription>
									</DialogHeader>
									<CreateLeagueForm :createLeague="createLeague" :isLoading="isLoading" />
								</DialogContent>
							</Dialog>

							<!-- DialogTrigger for Únete a una liga -->
							<Dialog v-model:open="isJoinDialogOpen">
								<DialogTrigger as-child>
									<a href="#"
										class="text-muted-foreground hover:text-foreground flex items-center gap-2">
										<SquarePlus class="h-7 w-7" />
										Únete a una liga
									</a>
								</DialogTrigger>
								<DialogContent
									class="w-[90%] sm:w-[80%] md:w-[70%] lg:w-[60%] max-w-none rounded-lg mx-auto p-6">
									<DialogHeader>
										<DialogTitle>Únete a una liga</DialogTitle>
										<DialogDescription>
											Introduzca el código de la liga para unirse.
										</DialogDescription>
									</DialogHeader>
									<JoinLeagueFrom :joinLeague="joinLeague" :isLoading="isLoading" />
								</DialogContent>
							</Dialog>
						</div>
					</nav>
				</SheetContent>
			</Sheet>

			<div class="flex w-full items-center gap-4 md:ml-auto md:gap-2 lg:gap-4">
				<p class="ml-auto flex-1 text-lg font-bold sm:flex-initial">¡Bienvenido {{ userName }}!</p>

				<DropdownMenu>
					<DropdownMenuTrigger as-child>
						<Button variant="secondary" size="icon" class="rounded-full">
							<CircleUser class="h-5 w-5" />
						</Button>
					</DropdownMenuTrigger>
					<DropdownMenuContent align="end">
						<!-- <DropdownMenuLabel>

						</DropdownMenuLabel> -->
						<DropdownMenuItem @click="toProfile">
							<Button variant="outline" class="w-full">Mi cuenta</Button>
						</DropdownMenuItem>

						<!-- <DropdownMenuSeparator /> -->
						<!-- <DropdownMenuItem>Soporte</DropdownMenuItem>
						<DropdownMenuSeparator /> -->
						<DropdownMenuItem @click="logout">
							<Button variant="destructive" class="w-full">Logout</Button>
						</DropdownMenuItem>
					</DropdownMenuContent>
				</DropdownMenu>
			</div>
		</header>
		<router-view />
	</div>
</template>