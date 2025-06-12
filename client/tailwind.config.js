/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}", // Scans all your component files
  ],
  theme: {
    extend: {
      colors: {
        // Our official lime/lemon green theme palette
        lime: {
          light: '#f4ffb8',
          DEFAULT: '#a0d911', // This allows you to use classes like `text-lime`, `bg-lime`
          dark: '#5b8c00'
        },
        // Our official neutral palette
        neutral: {
          text: '#333333',
          'text-light': '#666666',
          background: '#f7f7f7',
          border: '#e8e8e8'
        }
      }
    },
  },
  plugins: [],
};