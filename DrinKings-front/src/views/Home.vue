<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet'
import { CircleUser, Beer } from 'lucide-vue-next'
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

</script>

<template>
  <div class="flex min-h-screen w-full flex-col">
    <header class="sticky top-0 flex h-16 items-center gap-4 border-b bg-background px-4 md:px-6">
      <Sheet>
        <SheetTrigger as-child>
          <Button variant="outline" size="icon" class="shrink-0 "> <!--md:hidden -->
            <Beer class="h-5 w-5" />
          </Button>
        </SheetTrigger>
        <SheetContent side="left">
          <nav class="grid gap-6 text-lg font-medium">
            <div class="flex items-center gap-2 text-lg font-semibold">
              <Beer class="h-6 w-6" />
              <p>Tus ligas</p>
            </div>

            <a href="#" class="text-muted-foreground hover:text-foreground">
              Liga Leyendas
            </a>
          </nav>
        </SheetContent>
      </Sheet>
      <div class="flex w-full items-center gap-4 md:ml-auto md:gap-2 lg:gap-4">

        <p class="ml-auto flex-1 sm:flex-initial">¡Bienvenido {{ userName }}!</p>

        <DropdownMenu>
          <DropdownMenuTrigger as-child>
            <Button variant="secondary" size="icon" class="rounded-full">
              <CircleUser class="h-5 w-5" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Mi cuenta</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuItem>Soporte</DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem @click="logout">Logout</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </header>
    <main class="flex min-h-[calc(100vh_-_theme(spacing.16))] flex-1 flex-col gap-4 bg-muted/40 p-4 md:gap-8 md:p-10">
      <div class="mx-auto grid w-full max-w-6xl gap-2">
        <h1 class="text-3xl font-semibold">
          Liga:
        </h1>
      </div>
      <div class="mx-auto grid w-full max-w-6xl items-start gap-6 md:grid-cols-[180px_1fr] lg:grid-cols-[250px_1fr]">
        <div class="grid gap-6">
          <Card>
            <CardHeader>
              <CardTitle>Añade bebidas</CardTitle>
              <CardDescription>
                Marca aquí tus consumiciones.
              </CardDescription>
            </CardHeader>
            <CardContent class="flex flex-col gap-4">
              <div class="flex justify-between gap-2 my-4">
                <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg">
                  <img src="/cubata.jpg" alt="Cubata" class="rounded-lg w-20 " />
                </Button>
                <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg">
                  <img src="/tercio.png" alt="Tercio" class="rounded-lg h-24" />
                </Button>
                <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg">
                  <img src="/tercio.png" alt="Cubata" class="rounded-lg h-20" />
                </Button>
              </div>
            </CardContent>
            <CardFooter class="border-t px-6 py-4">
              <Button>Save</Button>
            </CardFooter>
          </Card>
        </div>
      </div>
    </main>
  </div>
</template>