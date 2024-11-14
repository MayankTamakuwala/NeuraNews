import { Button } from "@/components/ui/button";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useState } from "react";
import {axiosClient, isOk} from "@/lib/axiosClient";
import { useAuth } from "@/context/AuthContext";
import toast from "react-hot-toast";
import { useRouter } from "next/router";

export default function Authentication() {
    const [loading, setLoading] = useState(false);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");
    const {setUser} = useAuth()
    const router = useRouter();

    const handleLogin = async (e: any) => {
        e.preventDefault();
        setLoading(true);

        const res = await axiosClient.post("/auth/login", {
            email,
            password,
        });

        if(isOk(res)){
            toast.success("Login Successful")
            setUser({
                name: res.data.neuraData.name,
                email: email,
                refreshToken: res.data.neuraData.refresh_token,
                id: res.data.neuraData.id,
                token: res.data.neuraData.jwt
            })
            router.push("/")
        }
        
        setLoading(false);
    };

    const handleSignUp = async (e: any) => {
        e.preventDefault();
        setLoading(true);

        await axiosClient.post("/auth/signup", {
            name: name,
            email: email,
            password: password,
        }).then(()=>{toast.success("Account Created!")});
        setLoading(false);
    };

    return (
        <Tabs defaultValue="login" className="w-full lg:w-1/3">
            <TabsList
                className="grid w-full grid-cols-2"
                onChange={(e) => {
                    console.log(e);
                }}
            >
                <TabsTrigger value="login">Login</TabsTrigger>
                <TabsTrigger value="signup">Sign Up</TabsTrigger>
            </TabsList>
            <TabsContent value="login">
                <Card>
                    <CardHeader>
                        <CardTitle>Login</CardTitle>
                        <CardDescription>
                            {`Login to your NeuraNews Account`}
                        </CardDescription>
                    </CardHeader>
                    <form onSubmit={handleLogin}>
                        <CardContent className="space-y-2">
                            <div className="space-y-1">
                                <Label htmlFor="email">Email Address</Label>
                                <Input
                                    id="email"
                                    type="email"
                                    required
                                    disabled={loading}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="space-y-1">
                                <Label htmlFor="password">Password</Label>
                                <Input
                                    id="password"
                                    type="password"
                                    required
                                    disabled={loading}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                        </CardContent>
                        <CardFooter>
                            <Button type="submit" disabled={loading}>
                                {loading ? "Processing..." : "Login"}
                            </Button>
                        </CardFooter>
                    </form>
                </Card>
            </TabsContent>
            <TabsContent value="signup">
                <Card>
                    <CardHeader>
                        <CardTitle>Sign Up</CardTitle>
                        <CardDescription>{`Create new NeuraNews account.`}</CardDescription>
                    </CardHeader>
                    <form onSubmit={handleSignUp}>
                        <CardContent className="space-y-2">
                            <div className="space-y-1">
                                <Label htmlFor="name">Name</Label>
                                <Input
                                    id="name"
                                    required
                                    disabled={loading}
                                    onChange={(e) => setName(e.target.value)}
                                />
                            </div>
                            <div className="space-y-1">
                                <Label htmlFor="email">Email Address</Label>
                                <Input
                                    id="email"
                                    type="email"
                                    required
                                    disabled={loading}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="space-y-1">
                                <Label htmlFor="newpassword">New Password</Label>
                                <Input
                                    id="newpassword"
                                    type="password"
                                    required
                                    disabled={loading}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                        </CardContent>
                        <CardFooter>
                            <Button type="submit" disabled={loading}>
                                {loading ? "Processing..." : "Sign Up"}
                            </Button>
                        </CardFooter>
                    </form>
                </Card>
            </TabsContent>
        </Tabs>
    );
}
