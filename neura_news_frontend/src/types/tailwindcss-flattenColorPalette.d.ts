// types/tailwindcss-flattenColorPalette.d.ts
declare module "tailwindcss/lib/util/flattenColorPalette" {
	export default function flattenColorPalette(
		colors: Record<string, string | Record<string, string>>
	): Record<string, string>;
}
