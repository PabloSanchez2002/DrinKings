<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet'
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
            <div class="flex flex-col gap-2 ">
              <a href="#" class="text-muted-foreground hover:text-foreground flex items-center gap-2">
                <CirclePlus class="h-7 w-7" />
                Crea una liga
              </a>
              <a href="#" class="text-muted-foreground hover:text-foreground flex items-center gap-2">
                <SquarePlus class="h-7 w-7" />
                Unete a una liga
              </a>
            </div>
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