const API_BASE_URL = "http://localhost:8080/movies";

export const fetchMovies = async (page) => {
  try {
    const response = await fetch(`${API_BASE_URL}?page=${page}`);
    if (!response.ok) {
      throw new Error("Error fetching movies");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const fetchMovieDetails = async (id) => {
  try {
    const response = await fetch(`${API_BASE_URL}/movieDetails/${id}`);
    if (!response.ok) {
      throw new Error("Error fetching movie details");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const fetchTopRevenueMovies = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/topRevenue`);
    if (!response.ok) {
      throw new Error("Error fetching top revenue movies");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const fetchTopRevenueMoviesPerYear = async (year) => {
  try {
    const response = await fetch(`${API_BASE_URL}/topRevenue/${year}`);
    if (!response.ok) {
      throw new Error(`Error fetching top revenue movies for the year ${year}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
