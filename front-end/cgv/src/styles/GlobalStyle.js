import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyle = createGlobalStyle`
    ${reset}
    html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {
        font-family: 'Noto Sans KR', 'CJONLYONENEW', '맑은 고딕', '돋움', Dotum, sans-serif;
        font-size: 100%;
        margin: 0;
        padding: 0;
        border: 0;
        vertical-align: baseline;
        word-break: break-all;
        text-decoration: none;
    };
    ol, ul {
        list-style: none;
    };
    dl:after, ul:after, ol:after {
        content: '';
        clear: both;
        display: block;
    };
    input, select, img {
        vertical-align: middle;
    }
`;

export default GlobalStyle;