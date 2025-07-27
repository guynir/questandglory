import {Head, Html, Main, NextScript} from 'next/document';

export default function MyDocument() {
    return (
        <Html lang="en">
            <Head>
                {/* Common meta tags */}
                <meta charSet="utf-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1"/>

                <link rel="preconnect" href="https://fonts.googleapis.com"/>
                <link rel="preconnect" href="https://fonts.gstatic.com"/>
                <link rel="stylesheet"
                      href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
                />
            </Head>
            <body>
            <Main/>
            <NextScript/>
            </body>
        </Html>
    );
}