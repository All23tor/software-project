<!-- App.vue -->
<template>
  <div class="app">
    <div class="menu-card">
      <h1>🍔 Welcome to QuickBite!</h1>
      <p class="tagline">Delicious food, delivered fast.</p>

      <div class="food-items">
        <div class="food" v-for="item in featured" :key="item.name">
          <img :src="item.img" :alt="item.name" />
          <span>{{ item.name }}</span>
        </div>
      </div>

      <button @click="startOrder">Start Order 🍽️</button>
    </div>
  </div>
</template>

<script setup>
const featured = [
  { name: "Cheeseburger", img: "https://i.imgur.com/7D7I6dI.png" },
  { name: "Sushi", img: "https://i.imgur.com/sP4Y5ZK.png" },
  { name: "Pizza", img: "https://i.imgur.com/TlW5BzI.png" },
];

function startOrder() {
  alert("Redirecting to the menu...");
}
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
}

.menu-card {
  background: white;
  padding: 2.5rem 2rem;
  border-radius: 1.5rem;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 450px;
  width: 90%;
  animation: fadeIn 0.5s ease-out;
}

.menu-card h1 {
  font-size: 2.2rem;
  color: #e05656;
  margin-bottom: 0.5rem;
}

.menu-card .tagline {
  color: #444;
  font-size: 1.1rem;
  margin-bottom: 1.5rem;
}

.food-items {
  display: flex;
  justify-content: space-around;
  margin-bottom: 2rem;
  gap: 1rem;
}

.food {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-weight: 600;
  color: #333;
}

.food img {
  width: 70px;
  height: 70px;
  object-fit: contain;
  border-radius: 0.5rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 0.5rem;
  transition: transform 0.2s ease;
}

.food img:hover {
  transform: scale(1.1);
}

.menu-card button {
  background: #e05656;
  color: white;
  padding: 0.75rem 2rem;
  border: none;
  border-radius: 999px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease;
}

.menu-card button:hover {
  background: #c24545;
  transform: translateY(-2px);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>