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
import axios from "axios";
import { headers } from "next/headers";

export default function Authentication() {
  const [loading, setLoading] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");

  const handleLogin = async () => {
    setLoading(true);
    // const temp = await fetch("http://localhost:8080/api/auth/login", {
    //   method: "POST",
    //   body: JSON.stringify({
    //     email,
    //     password,
    //   }),
    //   headers: {
    //     "Content-Type": "application/json",
    //   },
    // });

    // console.log(temp)

    const temp2 = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        password,
      },
      {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true  
    });


    setLoading(false);
  };

  const handleSignUp = () => {
    setLoading(true);
    // Sign Up Login
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
              <Button type="button" disabled={loading} onClick={handleLogin}>
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
              <Button type="button" disabled={loading}>
                {loading ? "Processing..." : "Sign Up"}
              </Button>
            </CardFooter>
          </form>
        </Card>
      </TabsContent>
    </Tabs>
  );
}
