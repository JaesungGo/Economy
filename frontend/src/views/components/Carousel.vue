<template>
    <div class="card card-carousel overflow-hidden h-100 p-0">
        <div id="carouselExampleCaptions" class="carousel slide h-100" data-bs-ride="carousel">
            <div class="carousel-inner border-radius-lg h-100">
                <router-link to="/about" class="carousel-item h-100 active" 
                    :style="{
                        backgroundImage: 'url(' + require('@/assets/img/그린카드1.png') + ')',
                        backgroundSize: 'cover',
                        cursor: 'pointer'
                    }"
                />
                <router-link to="/about" class="carousel-item h-100"
                    :style="{
                        backgroundImage: 'url(' + require('@/assets/img/그린카드2.png') + ')',
                        backgroundSize: 'cover',
                        cursor: 'pointer'
                    }"
                />
                <router-link to="/about" class="carousel-item h-100"
                    :style="{
                        backgroundImage: 'url(' + require('@/assets/img/그린카드3.png') + ')',
                        backgroundSize: 'cover',
                        cursor: 'pointer'
                    }"
                />
            </div>
            <button class="carousel-control-prev w-5 me-3" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next w-5 me-3" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
</template>

<script setup>
</script>