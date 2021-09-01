# Introduction
Refactoring du projet 3 en MVVM (qui était initialement en MVC), avec la plupart des bonnes pratiques associées 
au MVVM. C'est une bonne base pour apprendre le MVVM : vous connaissez déjà le projet, je n'ai pas beaucoup touché
les XML (je les ai juste un peu remis au goût du jour), les seules différences concernent la transformation en
architecture MVVM.  

Bonne découverte ! 

# Sujets abordés / démontrés
 * Architecture MVVM (Model View ViewModel)
 * `LiveData` (en particulier `MutableLiveData` dans un `repository`)
 * Utilisation d'un `Fragment` comme vue (`NeighboursFragment`)
 * Utilisation d'une `Activity` comme vue (`NeighbourDetailActivity`)
 * `RecyclerView` (et son `ListAdapter` / `DiffItemCallback`)
 * Dialogue entre un `Adapter` et son `Activity` (via l'interface `OnMeetingClickedListener`)
 * Utilisation d'un Repository pour persister les différents Neighbours pendant la vie de l'Application (`NeighbourRepository`)
 * Singleton et Injection (`ViewModelFactory`)
 * Tests unitaires (TU) avec des `LiveData` et `ViewModels` (grâce à `Mockito`)