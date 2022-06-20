
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './page/home/home';
import GlobalStyle from './styles/GlobalStyle';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './page/login/login';
import Join from './page/join/join';
import Mycgv from './page/mycgv/mycgv';
import AllMovies from './page/allMovies/allMovies';
import Theater from './page/theater/theater';
import MovieDetail from './page/movieDetail/movieDetail';
import Reserve from './page/reserve/reserve';
import Admin from './page/admin/admin';

function App() {
  return (
    <>
      <GlobalStyle/>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home/>} />
          <Route path='/login' element={<Login/>} />
          <Route path='/join' element={<Join/>} />
          <Route path='/mycgv' element={<Mycgv/>} />
          <Route path='/movies' element={<AllMovies/>} />
          <Route path='/movies/detail/:id' element={<MovieDetail/>} />
          <Route path='/theater' element={<Theater/>} />
          <Route path='/reserve' element={<Reserve/>} />
          <Route path='/admin' element={<Admin/>} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
