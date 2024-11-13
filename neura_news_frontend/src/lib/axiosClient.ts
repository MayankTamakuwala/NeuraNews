import axios from 'axios';
import Cookies from "js-cookie";

interface AuthResponse {
	data: {
		refresh_token: string;
		jwt: string;
	};
}

const axiosClient = axios.create({
        baseURL: "http://localhost:8080/api",
    })

axiosClient.interceptors.request.use(
	function (config) {
		if (config.url == "/auth/login" || config.url == "/auth/signup") {
			return config;
		}

        // const token = jar.getCookies("http://localhost:8080");
        // console.log(token)

		config.withCredentials = true;
		return config;
	},
	function (error) {
		return Promise.reject(error);
	}
);

axiosClient.interceptors.response.use(
	function (response) {
		if (
			response.config.url == "/auth/login" ||
			response.config.url == "/auth/refresh"
		){
			const authResponse = response as AuthResponse;

			// Set JWT cookie with appropriate options
			Cookies.set("jwt", authResponse.data.jwt, {
				expires: 0.06666666667,
				path: "/",
				secure: process.env.NODE_ENV === "production", // Only use HTTPS in production
				sameSite: "strict",
			});

			// Set refresh token cookie with longer expiration
			Cookies.set('refresh_token', authResponse.data.refresh_token, {
				expires: 7,
				path: "/",
				secure: process.env.NODE_ENV === "production",
				sameSite: "strict",
			});
		}
		return response;
	},
	function (error) {
		return Promise.reject(error);
	}
);

export default axiosClient;