import { User } from "@/types";
import { createContext, useContext, useState } from "react";

type AuthContext = {
    user : User | null,
    setUser : (u: User) => void
}

const AuthContext = createContext<AuthContext>({user: null, setUser: ()=>{}});

export function AuthProvider({children} : {children: React.ReactNode}) {
    const [user, setUser] = useState<User | null>(null);

    return (
        <AuthContext.Provider value={{ user, setUser }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    return context
}
