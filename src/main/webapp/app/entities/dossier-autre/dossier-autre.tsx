import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDossierAutre } from 'app/shared/model/dossier-autre.model';
import { getEntities } from './dossier-autre.reducer';

export const DossierAutre = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const dossierAutreList = useAppSelector(state => state.dossierAutre.entities);
  const loading = useAppSelector(state => state.dossierAutre.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="dossier-autre-heading" data-cy="DossierAutreHeading">
        <Translate contentKey="drugStoreApp.dossierAutre.home.title">Dossier Autres</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.dossierAutre.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/dossier-autre/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.dossierAutre.home.createLabel">Create new Dossier Autre</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dossierAutreList && dossierAutreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.numeroEnvoi">Numero Envoi</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.dateDepot">Date Depot</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.dateEnvoi">Date Envoi</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.typeDemendeur">Type Demendeur</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.profession">Profession</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.nature">Nature</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.statut">Statut</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.dateDerniereModif">Date Derniere Modif</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.commentaire">Commentaire</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.telephone">Telephone</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.cIN">C IN</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.raisonSocial">Raison Social</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.iCE">I CE</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.numRC">Num RC</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.iF">I F</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.numAffiliation">Num Affiliation</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.documentList">Document List</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.utilisateur">Utilisateur</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.dossierAutre.local">Local</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dossierAutreList.map((dossierAutre, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/dossier-autre/${dossierAutre.id}`} color="link" size="sm">
                      {dossierAutre.id}
                    </Button>
                  </td>
                  <td>{dossierAutre.numero}</td>
                  <td>{dossierAutre.numeroEnvoi}</td>
                  <td>
                    {dossierAutre.dateDepot ? <TextFormat type="date" value={dossierAutre.dateDepot} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {dossierAutre.dateEnvoi ? <TextFormat type="date" value={dossierAutre.dateEnvoi} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{dossierAutre.typeDemendeur}</td>
                  <td>{dossierAutre.profession}</td>
                  <td>{dossierAutre.nature}</td>
                  <td>{dossierAutre.statut}</td>
                  <td>
                    {dossierAutre.dateDerniereModif ? (
                      <TextFormat type="date" value={dossierAutre.dateDerniereModif} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{dossierAutre.commentaire}</td>
                  <td>{dossierAutre.nom}</td>
                  <td>{dossierAutre.prenom}</td>
                  <td>{dossierAutre.telephone}</td>
                  <td>{dossierAutre.cIN}</td>
                  <td>{dossierAutre.raisonSocial}</td>
                  <td>{dossierAutre.iCE}</td>
                  <td>{dossierAutre.numRC}</td>
                  <td>{dossierAutre.iF}</td>
                  <td>{dossierAutre.numAffiliation}</td>
                  <td>{dossierAutre.user ? dossierAutre.user.id : ''}</td>
                  <td>
                    {dossierAutre.documentLists
                      ? dossierAutre.documentLists.map((val, j) => (
                          <span key={j}>
                            <Link to={`/document/${val.id}`}>{val.id}</Link>
                            {j === dossierAutre.documentLists.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {dossierAutre.utilisateur ? (
                      <Link to={`/utilisateur/${dossierAutre.utilisateur.id}`}>{dossierAutre.utilisateur.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{dossierAutre.local ? <Link to={`/local/${dossierAutre.local.id}`}>{dossierAutre.local.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/dossier-autre/${dossierAutre.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/dossier-autre/${dossierAutre.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/dossier-autre/${dossierAutre.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="drugStoreApp.dossierAutre.home.notFound">No Dossier Autres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DossierAutre;
