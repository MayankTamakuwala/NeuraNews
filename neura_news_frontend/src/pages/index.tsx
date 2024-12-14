import { Button } from "@/components/ui/button";
import { useAuth } from "@/context/AuthContext";
import { axiosClient } from "@/lib/axiosClient";
// import Image from "next/image";
import { useRouter } from "next/router";
import { useEffect } from "react";

export default function Home() {
    const {user} = useAuth();

    const router = useRouter();

    useEffect(() => {
        if(!user){
            router.push("/authenticate")
        }
    }, [])

    const textClick = async() => {
        axiosClient.get("/test")
    }

    return (
        <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
            Thai gayu
            <Button onClick={textClick}>
                Test
            </Button>
        </div>
    );
}
