const rawBaseUrl = import.meta.env.VITE_BACKEND_BASE_URL ?? ''

export const API_BASE_URL = rawBaseUrl.replace(/\/$/, '')
export const POLL_INTERVAL_MS = 1000
