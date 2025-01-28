<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet'
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { CircleUser, Beer, LoaderCircle } from 'lucide-vue-next'
import { CirclePlus, SquarePlus } from 'lucide-vue-next';
import { useRouter } from 'vue-router'
import { jwtDecode } from 'jwt-decode';
import { ref } from 'vue';
import apiClient from '@/services/apiClient'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { useForm } from 'vee-validate'
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
const router = useRouter()
const isLoading = ref(false)

// Validation schema
const formSchema = toTypedSchema(
	z.object({
		name: z.string().min(3, { message: 'El nombre debe de ser de 3 o mas caracteres' }),
		description: z.string().min(3, { message: 'La descripción es requerida' }),
	})
)
const joinLeagueSchema = toTypedSchema(
	z.object({
		shareToken: z.string().min(1, { message: 'El código de la liga es requerido' }),
	})
)

const { handleSubmit, isFieldDirty } = useForm({
	validationSchema: formSchema,
})

const { handleSubmit: handleSubmitJoin, isFieldDirty: isFieldDirtyJoin } = useForm({
	validationSchema: joinLeagueSchema,
})

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

const goToLeague = (leagueId: number) => {
	router.push(`/home/league/${leagueId}`);
	isSheetOpen.value = false;
}

const onSubmitCreate = handleSubmit(async (values) => {
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
			joinLeague(response.data.shareToken)
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

})

const onSubmitJoin = handleSubmitJoin(async (values: any) => {
	isLoading.value = true

	console.log('Form submitted with:', values.shareToken.value)

	apiClient.post('/league/join', values, {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	}).then((response) => {
		if (response.status === 200) {
			toast({
				title: 'Éxito',
				description: 'Liga ' + response.data.name + ' unida correctamente.',
				duration: 2000,
			})
			reloadLeagues()
			goToLeague(response.data.id)
			isJoinDialogOpen.value = false
		}
		else {
			toast({
				title: 'Error',
				description: response.data.message || 'Error al unirse a la liga',
				variant: 'destructive',
			})
		}
	}).catch(error => {
		toast({
			title: 'Error',
			description: error.response.data.message || 'No se pudo unir a la liga. Inténtalo de nuevo.',
			variant: 'destructive',
		})
	}).finally(() => {
		isLoading.value = false
	})
})


const joinLeague = async (shareToken: any) => {
	// Implement the joinLeague function
	console.log('Joining league with shareToken:', shareToken)
	apiClient.post('/league/join', { shareToken }, {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	})
		.then((response) => {
			toast({
				title: 'Éxito',
				description: 'UNido a liga ' + response.data.name + ' correctamente.',
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
			// isLoading.value = false
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
					<nav class="h-full grid gap-6  font-medium">
						<!-- Title Section -->
						<div class="flex gap-2 text-2xl font-semibold">
							<Beer class="h-6 w-6" />
							<p>Tus ligas</p>
						</div>

						<!-- Main Content -->
						<div class="flex flex-col text-2xl font-semibold  justify-between">
							<!-- Ligas -->
							<div v-for="league in userLeagues" :key="league.id">
								<a href="#" class="text-muted-foreground hover:text-foreground"
									@click.prevent="goToLeague(league.id)">
									{{ league.name }}
								</a>
							</div>
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
										<form @submit="onSubmitCreate" class="w-90 mx-auto space-y-3">
											<!-- Name Field -->
											<FormField v-slot="{ componentField }" name="name"
												:validate-on-blur="!isFieldDirty">
												<div class="grid grid-cols-4 items-center gap-4">
													<Label for="name" class="text-right">Nombre</Label>
													<FormControl>
														<Input id="name" placeholder="Nombre de la liga"
															class="col-span-3" v-bind="componentField" />
													</FormControl>
													<FormMessage class="col-span-4 text-sm text-red-500" />
												</div>
											</FormField>
											<!-- Description Field -->
											<FormField v-slot="{ componentField }" name="description"
												:validate-on-blur="!isFieldDirty">
												<div class="grid grid-cols-4 items-center gap-4">
													<Label for="description" class="text-right">Descripción</Label>
													<FormControl>
														<Input id="description" placeholder="Descripción"
															class="col-span-3" v-bind="componentField" />
													</FormControl>
													<FormMessage class="col-span-4 text-sm text-red-500" />
												</div>
											</FormField>
											<!-- Form Footer -->
											<DialogFooter class="gap-4">
												<DialogClose as-child>
													<Button variant="outline">Cancel</Button>
												</DialogClose>
												<Button type="submit" :disabled="isLoading">
													<LoaderCircle v-if="isLoading" class="w-5 h-5 mr-2" />
													Crear una liga!
												</Button>
											</DialogFooter>
										</form>
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
										<form @submit="onSubmitJoin" class="w-90 mx-auto space-y-3">
											<!-- Share Token Field -->
											<FormField v-slot="{ componentField }" name="shareToken"
												:validate-on-blur="!isFieldDirtyJoin">
												<div class="grid grid-cols-4 items-center gap-4">
													<Label for="shareToken" class="text-right">Código</Label>
													<FormControl>
														<Input id="shareToken" placeholder="Código de la liga"
															class="col-span-3" v-bind="componentField" />
													</FormControl>
													<FormMessage class="col-span-4 text-sm text-red-500" />
												</div>
											</FormField>
											<!-- Form Footer -->
											<DialogFooter class="gap-4">
												<DialogClose as-child>
													<Button variant="outline">Cancel</Button>
												</DialogClose>
												<Button type="submit" :disabled="isLoading">
													<LoaderCircle v-if="isLoading" class="w-5 h-5 mr-2" />
													Únete a la liga!
												</Button>
											</DialogFooter>
										</form>
									</DialogContent>
								</Dialog>
							</div>

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
						<DropdownMenuLabel @click="router.push('profile')">Mi cuenta</DropdownMenuLabel>
						<DropdownMenuSeparator />
						<DropdownMenuItem>Soporte</DropdownMenuItem>
						<DropdownMenuSeparator />
						<DropdownMenuItem @click="logout">Logout</DropdownMenuItem>
					</DropdownMenuContent>
				</DropdownMenu>
			</div>
		</header>
		<router-view />
	</div>
</template>