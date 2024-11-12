import "@/styles/globals.css";
import { AppProps } from "next/app";
import axios from "axios";

import Head from "next/head";

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    if (
      config.url == "http://localhost:8080/api/auth/login" ||
      config.url == "http://localhost:8080/api/auth/signup"
    ) {
      return config;
    }

    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  function (response) {
    // Do something before request is sent
    //   if (config.url == "http://localhost:8080/api/auth/login" || config.url == "http://localhost:8080/api/auth/signup"){
    //       return config
    //   }
    const setCookieHeader = response.headers;

    console.log(setCookieHeader);
    // console.log(response.headers["jwt"]);

    return response;
  },
  function (error) {
    return Promise.reject(error);
  }
);

export default function App({ Component, pageProps }: AppProps) {
  return (
    <div className="h-screen flex justify-center items-center p-5">
      <Head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="theme-color" content="#000000" />
        {/* <link rel="manifest" href="manifest.json" />
                <link rel="icon" type="image/x-icon" href="favicon.ico" />
                <link rel="apple-touch-icon" sizes="180x180" href="apple-touch-icon.png" />
                <link rel="icon" type="image/png" sizes="32x32" href="favicon-32x32.png" />
                <link rel="icon" type="image/png" sizes="16x16" href="favicon-16x16.png" /> */}
      </Head>
      <Component {...pageProps} />
    </div>
  );
}
