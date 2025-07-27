import {dirname} from "path";
import {fileURLToPath} from "url";
import {FlatCompat} from "@eslint/eslintrc";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const compat = new FlatCompat({
    baseDirectory: __dirname,
});

const eslintConfig = [
    ...compat.config({
        extends: ["next/core-web-vitals", "next/typescript"],
        rules: {
            "@next/next/no-document-import-in-page": "off", // Next.js does not require React to be in scope
            "@typescript-eslint/no-explicit-any": "off", // Allow usage of 'any' type
        }
    })

];

export default eslintConfig;
