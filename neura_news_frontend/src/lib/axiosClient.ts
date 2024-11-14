import axios from 'axios';
import Cookies from "js-cookie";
import toast from 'react-hot-toast';

type AuthResponse = {
	neuraData: {
		refresh_token: string;
		jwt: string;
		email: string;
		id: string;
		name: string;
	};
}

const axiosClient = axios.create({
    baseURL: "http://localhost:8080/api",
})

axiosClient.defaults.headers.common["Content-Type"] = "application/json"

axiosClient.interceptors.request.use(
	function (config) {
		if (config.url == "/auth/login" || config.url == "/auth/signup") {
			return config;
		}

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
			console.log(response)
			const authResponse = response.data as AuthResponse;
			console.log("auth:", authResponse)

			Cookies.set("jwt", authResponse.neuraData.jwt, {
				expires: 0.006666666667,
				path: "/",
				secure: process.env.NODE_ENV === "production", // Only use HTTPS in production
				sameSite: "strict",
			});

			Cookies.set('refresh_token', authResponse.neuraData.refresh_token, {
				expires: 7,
				path: "/",
				secure: process.env.NODE_ENV === "production",
				sameSite: "strict",
			});
		}
		return response;
	},
	function (error) {
		console.log(error)
		toast.error(error.response.data.message)
		return {status: error.response.status}
		// return Promise.reject(error)
	}
);

const isOk = (res: any) => {
	return (res.status === 200 || res.status === 201)
}

export {axiosClient, isOk};
