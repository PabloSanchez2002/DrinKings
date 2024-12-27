<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet'
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { CircleUser, Beer } from 'lucide-vue-next'
import { CirclePlus, SquarePlus } from 'lucide-vue-next';
import { useRouter } from 'vue-router'
import { jwtDecode } from 'jwt-decode';
import { ref } from 'vue';

const token = localStorage.getItem('auth_token');
const userName = ref('');
const router = useRouter()


if (token) {
	// Decode the JWT
	const decodedToken: any = jwtDecode(token);

	userName.value = decodedToken?.sub;
	console.log(`Welcome, ${userName.value}`);
} else {
	console.log('No token found');
}

const logout = () => {
	// Remove token from localStorage
	localStorage.removeItem('auth_token')

	console.log('Logged out');

	router.push('/access/login')
}

const createLeague = () => {
	console.log('Creating league');
	// import axios from 'axios';

	// const leagueData = {
	// 	name: 'Liga1',
	// 	description: 'Liga prueba EDIT4'
	// };

	// axios.post('/league', leagueData)
	// 	.then(response => {
	// 		console.log('League created successfully:', response.data);
	// 	})
	// 	.catch(error => {
	// 		console.error('Error creating league:', error);
	// 	});
}

</script>

<template>
	<div class="flex min-h-screen w-full flex-col">
		<header class="sticky top-0 flex h-16 items-center gap-4 border-b bg-background px-4 md:px-6">
			<Sheet>
				<SheetTrigger as-child>
					<Button variant="outline" size="icon" class="shrink-0 h-12 w-12">
						<Beer class="h-10 w-10" />
					</Button>
				</SheetTrigger>
				<SheetContent side="left" class="">
					<nav class="grid gap-6 text-lg font-medium">
						<div class="flex items-center gap-2 text-2xl font-semibold ">
							<Beer class="h-6 w-6" />
							<p>Tus ligas</p>
						</div>

						<a href="#" class="text-muted-foreground hover:text-foreground">
							Liga Leyendas
						</a>

						<div class="flex flex-col gap-2">
							<!-- DialogTrigger for Crea una liga -->
							<Dialog>
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
									<form @submit.prevent="createLeague()" class="w-full">
										<div class="grid gap-4 py-4">
											<!-- Name Field -->
											<div class="grid grid-cols-4 items-center gap-4">
												<Label for="name" class="text-right">Nombre</Label>
												<Input id="name" placeholder="Nombre de la liga" class="col-span-3" />
											</div>
											<!-- Password Field -->
											<div class="grid grid-cols-4 items-center gap-4">
												<Label for="description" class="text-right">Descripción</Label>
												<Input id="description" placeholder="Descripción" class="col-span-3" />
											</div>
										</div>
										<!-- Form Footer -->
										<DialogFooter class="gap-4">
											<DialogClose as-child>
												<Button variant="outline">Cancel</Button>
											</DialogClose>
											<DialogClose as-child>
												<Button type="submit">Crear una liga!</Button>
											</DialogClose>
										</DialogFooter>
									</form>
								</DialogContent>
							</Dialog>

							<!-- DialogTrigger for Únete a una liga -->
							<Dialog>
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
									<div class="grid gap-4 py-4">
										<div class="grid grid-cols-4 items-center gap-4">
											<Label for="league-code" class="text-right">
												Código
											</Label>
											<Input id="league-code" placeholder="Código de la liga"
												class="col-span-3" />
										</div>
									</div>
									<DialogFooter class="gap-4">
										<!-- Cancel Button with Dialog.Close -->
										<DialogClose as-child>
											<Button variant="outline">Cancel</Button>
											<Button type="submit">Unete a la liga!</Button>
										</DialogClose>
									</DialogFooter>
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