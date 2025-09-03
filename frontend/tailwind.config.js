// tailwind.config.js
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
        colors: {
            bg: {
            dark: "#1c1c1e",
            },
            text: {
            light: "#f5f5f7",
            },
            accent: {
            blue: "#0a84ff",
            hover: "#0060df",
            },
            highlight: {
            gray: "#2c2c2e",
            },
            border: {
            muted: "#3a3a3c",
            },
        },
    }
  },
  plugins: [],
};
