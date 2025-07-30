<template>
  <div class="app">
    <MenuCard/>
  </div>
</template>

<script setup>
import MenuCard from './components/MenuCard.vue'
</script>

<style>
body {
  margin: 0;
  font-family: 'Segoe UI', sans-serif;
  background: linear-gradient(to bottom right, #ffe7e7, #fff5d6);
}

.app {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 2rem;
}
</style>