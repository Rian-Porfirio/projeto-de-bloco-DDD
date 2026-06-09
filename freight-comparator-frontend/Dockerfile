FROM node:22-alpine AS deps
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm ci

FROM node:22-alpine AS build
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .
ARG VITE_API_URL
RUN VITE_API_URL="$VITE_API_URL" npm run build

FROM node:22-alpine AS runtime
WORKDIR /app
ENV NODE_ENV=production
RUN npm install -g serve@14
COPY --from=build /app/dist ./dist
USER node
EXPOSE 3000
CMD ["serve", "-s", "dist", "-l", "3000"]